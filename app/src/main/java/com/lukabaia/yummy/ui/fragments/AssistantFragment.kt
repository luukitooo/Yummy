package com.lukabaia.yummy.ui.fragments

import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.lukabaia.yummy.adapter.MessagesAdapter
import com.lukabaia.yummy.databinding.FragmentAssistantBinding
import com.lukabaia.yummy.model.assistant.Message
import com.lukabaia.yummy.model.assistant.SuggestsInfo
import com.lukabaia.yummy.ui.fragments.base.BaseFragment
import com.lukabaia.yummy.viewModels.AssistantViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AssistantFragment :
    BaseFragment<FragmentAssistantBinding>(FragmentAssistantBinding::inflate) {

    private val viewModel by viewModel<AssistantViewModel>()

    private val messagesAdapter by lazy { MessagesAdapter() }

    override fun init() {
        binding.rvMessages.adapter = messagesAdapter
        messagesAdapter.submitList(mutableListOf<Message>())
    }

    override fun listeners(): Unit = with(binding) {
        btnBack.setOnClickListener {
            requireActivity().onBackPressed()
        }
        btnHelp.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getSuggests(
                    query = etMessage.text.toString()
                )
            }
        }
        btnSend.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val message = binding.etMessage.text.toString()
                handleSending(
                    text = message
                )
                viewModel.getAssistantAnswer(
                    text = message
                )
            }
        }
        etMessage.doOnTextChanged { text, _, _, _ ->
            btnSend.isEnabled = !text.isNullOrEmpty()
        }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.suggestsFlow.collect { suggestsInfo ->
                buildAutocompleteEditText(suggestsInfo)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.answerFlow.collect { assistantAnswer ->
                addToRecyclerView(
                    Message(
                        id = messagesAdapter.currentList.size + 1,
                        message = assistantAnswer.answerText ?: "No Answer...",
                        fromUser = false
                    )
                )
            }
        }
    }

    private suspend fun handleSending(text: String) {
        viewModel.getAssistantAnswer(text)
        binding.etMessage.setText("")
        addToRecyclerView(
            Message(
                id = messagesAdapter.currentList.size + 1,
                message = text,
                fromUser = true
            )
        )
    }

    private fun buildAutocompleteEditText(suggestsInfo: SuggestsInfo) {
        val result = suggestsInfo.suggests?.names?.map { it.name } ?: emptyList()
        binding.etMessage.apply {
            setSimpleItems(result.toTypedArray())
            showDropDown()
        }
    }

    private fun addToRecyclerView(message: Message) {
        val currentMessages = mutableListOf<Message>()
        currentMessages.apply {
            addAll(messagesAdapter.currentList)
            add(message)
        }
        messagesAdapter.submitList(currentMessages)
    }

}