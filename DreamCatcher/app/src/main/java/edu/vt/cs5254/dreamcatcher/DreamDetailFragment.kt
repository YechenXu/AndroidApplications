package edu.vt.cs5254.dreamcatcher

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.icu.text.MessageFormat.format
import android.net.Uri
import android.os.Bundle
import android.text.format.DateFormat.format
import android.util.Log
import android.view.*
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.view.MenuProvider
import androidx.core.view.doOnLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.vt.cs5254.dreamcatcher.databinding.FragmentDreamDetailBinding
import junit.framework.Assert.format
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File
import java.lang.String.format
import java.text.DateFormat
import java.text.MessageFormat.format
import java.text.SimpleDateFormat
import java.util.*

class DreamDetailFragment : Fragment() {

    private val args: DreamDetailFragmentArgs by navArgs()

    private val vm: DreamDetailViewModel by viewModels() {
        DreamDetailViewModelFactory(args.dreamId)
    }

    private var _binding: FragmentDreamDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "FragmentDreamDetailBinding is null"
        }

    private val takePhoto = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ){ didTakePhoto: Boolean ->
        //handle the result
        if (didTakePhoto) {
            binding.dreamPhoto.tag = null
            vm.dream.value?.let { updatePhoto(it) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDreamDetailBinding.inflate(inflater, container, false)
        binding.dreamEntryRecycler.layoutManager = LinearLayoutManager(context)

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_dream_detail, menu)

                val captureImageIntent = takePhoto.contract.createIntent(
                    requireContext(),
                    Uri.EMPTY // NOTE: The "null" used in BNRG is obsolete now
                )
                menu.findItem(R.id.take_photo_menu).isVisible = canResolveIntent(captureImageIntent)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId){
                    R.id.share_dream_menu -> {
                        vm.dream.value?.let { shareDream(it) }
                        true
                    }

                    R.id.take_photo_menu -> {
                        vm.dream.value?.let {
                            val photoFile = File(
                                requireContext().applicationContext.filesDir,
                                it.photoFileName
                            )
                            val photoUri = FileProvider.getUriForFile(
                                requireContext(),
                                "edu.vt.cs5254.dreamcatcher.fileprovider",
                                photoFile
                            )
                            takePhoto.launch(photoUri)
                        }
                        true
                    }
                    else -> false
                }
            }
        },
            viewLifecycleOwner
        )

        getItemTouchHelper().attachToRecyclerView(binding.dreamEntryRecycler)

        binding.dreamEntryRecycler.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    private fun shareDream(dream: Dream){
        val reportIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, getDreamReport(dream) )
            putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.dream_report_subject)
            )
        }
        val chooseIntent = Intent.createChooser(reportIntent,getString(R.string.send_report))
        startActivity(chooseIntent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.dream.collect() { dream ->
                    dream?.let { updateView(dream) }
                }
            }
        }

        binding.fulfilledCheckbox.setOnClickListener {
            vm.updateDream { oldDream ->
                if (oldDream.isFulfilled) {
                    oldDream.copy().apply { entries = oldDream.entries.dropLast(1) }
                } else {
                    oldDream.copy().apply {
                        entries = oldDream.entries + DreamEntry(
                            kind = DreamEntryKind.FULFILLED,
                            dreamId = oldDream.id
                        )
                    }
                }
            }
        }

        binding.deferredCheckbox.setOnClickListener {
            vm.updateDream { oldDream ->
                if (oldDream.isDeferred) {
                    oldDream.copy().apply {
                        entries = oldDream.entries - oldDream.entries.filterNot { entry -> entry.kind == DreamEntryKind.REFLECTION
                        }.last() }
                } else {
                    oldDream.copy().apply {
                        entries = oldDream.entries + DreamEntry(
                            kind = DreamEntryKind.DEFERRED,
                            dreamId = oldDream.id
                        )
                    }
                }
            }
        }

        binding.titleText.doOnTextChanged { text, _, _, _ ->
            vm.updateDream { oldDream ->
                oldDream.copy(title = text.toString()).apply { entries = oldDream.entries }
            }
        }

        binding.dreamPhoto.setOnClickListener {
            findNavController().navigate(
                DreamDetailFragmentDirections.showPhotoDetail(vm.dream.value?.photoFileName.toString())
            )
        }

        binding.addReflectionButton.setOnClickListener {
            ReflectionDialogFragment().show(parentFragmentManager, ReflectionDialogFragment.REQUEST_KEY)
        }


        parentFragmentManager.setFragmentResultListener(
            ReflectionDialogFragment.REQUEST_KEY,
            viewLifecycleOwner
        )
        { _, bundle ->
            val entryText = bundle.getString(ReflectionDialogFragment.BUNDLE_KEY, "")

            vm.updateDream { oldDream ->
                oldDream.copy().apply {
                    entries = oldDream.entries + DreamEntry(
                        dreamId = oldDream.id,
                        kind = DreamEntryKind.REFLECTION,
                        text = entryText
                    )
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateView(dream: Dream) {

        if (dream.isFulfilled) {
            binding.addReflectionButton.hide()
        }else{
            binding.addReflectionButton.show()
        }
        val dateString = SimpleDateFormat("yyyy-MM-dd 'at' hh:mm:ss a").format(dream.lastUpdated)
        binding.lastUpdatedText.text = "Last updated $dateString"

        if (binding.titleText.toString() != dream.title) {
            binding.titleText.setText(dream.title)
        }

        binding.deferredCheckbox.isChecked = dream.isDeferred
        binding.fulfilledCheckbox.isChecked = dream.isFulfilled
        binding.fulfilledCheckbox.isEnabled = !dream.isFulfilled
        binding.deferredCheckbox.isEnabled = !dream.isDeferred

        binding.dreamEntryRecycler.adapter = DreamEntryAdapter(dream.entries)

        updatePhoto(dream)
    }

    private fun updatePhoto(dream: Dream) {
        with(binding.dreamPhoto) {
            if (tag != dream.photoFileName) {
                val photoFile =
                    File(requireContext().applicationContext.filesDir, dream.photoFileName)
                if (photoFile.exists()) {
                    doOnLayout { measuredView ->
                        val scaledBM = getScaledBitmap(
                            photoFile.path,
                            measuredView.width,
                            measuredView.height
                        )
                        setImageBitmap(scaledBM)
                        tag = dream.photoFileName
                    }
                    isEnabled = true;
                } else {
                    isEnabled = false;
                    setImageBitmap(null)
                    tag = null
                }
            }
        }
    }

    private fun getDreamReport(dream: Dream): String {
        val dreamTitle = dream.title + "\n"

        val formattedDate = android.text.format.DateFormat.format(" yyyy-MM-dd 'at' hh:mm:ss a", dream.lastUpdated)
        val time = "Last updated$formattedDate \n"

        var reflections = ""
        if (dream.entries.any { it.kind == DreamEntryKind.REFLECTION }) {
            reflections = getString(R.string.dream_report_reflections) + "\n"
            reflections += dream.entries.filter { it.kind == DreamEntryKind.REFLECTION }
                .joinToString(separator = "") {
                    " * ${it.text} \n"
                }
        }

        var result = ""
        if (dream.isFulfilled) result = getString(R.string.dream_report_fulfilled)
        else if (dream.isDeferred) result = getString(R.string.dream_report_deferred)
        println("share")
        println(getString(R.string.dream_report, dreamTitle, time, reflections, result))

        return getString(R.string.dream_report, dreamTitle, time, reflections, result)
    }

    private fun canResolveIntent(intent: Intent): Boolean {
        val packageManager: PackageManager = requireActivity().packageManager
        val resolvedActivity: ResolveInfo? =
            packageManager.resolveActivity(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
        return resolvedActivity != null
    }

    private fun getItemTouchHelper(): ItemTouchHelper {

        return ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, 0) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewLifecycleOwner.lifecycleScope.launch {
                    val entryHolder = viewHolder as DreamEntryHolder
                    val entryDeleted = entryHolder.boundEntry
                    vm.updateDream { oldDream ->
                        oldDream.copy().apply {
                            entries = oldDream.entries.filter { it!=entryDeleted }
                        }
                    }
                }

            }

            override fun getSwipeDirs(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val entryHolder = viewHolder as DreamEntryHolder
                val entryDeleted = entryHolder.boundEntry
                if (entryDeleted.kind == DreamEntryKind.REFLECTION){
                    return ItemTouchHelper.LEFT
                }else{
                    return 0
                }
            }

        })
    }

}