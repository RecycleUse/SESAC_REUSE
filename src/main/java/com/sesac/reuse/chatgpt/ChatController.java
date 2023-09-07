package com.sesac.reuse.chatgpt;

import com.sesac.reuse.entity.item.Item;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "openai", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatController {
    @Value("${openai.api.chat.default.role}")
    private String defaultRole;

    @Autowired
    private ChatGPTClientService chatGPTClientService;

    @GetMapping("chat/{prompt}")
    public String chatGptRequest(@PathVariable String prompt) {

        final OpenAiService service = chatGPTClientService.getOpenAiService();
        final ChatCompletionRequest chatRequest = chatGPTClientService.getChatCompletionRequest(List.of(new ChatMessage(defaultRole, prompt +"재활용을어떻게해야할까요?")));

        return service.createChatCompletion(chatRequest).getChoices().get(0).getMessage().getContent();
    }

}
