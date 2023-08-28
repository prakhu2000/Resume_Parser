package com.example.resume_parser;

import java.io.IOException;
import java.util.List;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tika.Tika;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ResumeParserApplication {

	@RequestMapping("/")
    public String index() {
        return "index";
    }
	
	@PostMapping("/upload")
    public String uploadFile(@RequestParam("resume") MultipartFile file, Model model) throws IOException {
        String extractedText = extractTextFromResume(file.getInputStream());
        List<String> extractedEmails = extractEmails(extractedText);

        model.addAttribute("emails", extractedEmails);
        return "index";
    }

	private String extractTextFromResume(InputStream inputStream) throws IOException {
		try {
			Tika tika = new Tika();
        	return tika.parseToString(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }

    private List<String> extractEmails(String text) {
        List<String> emails = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,7}\\b");
        Matcher matcher = pattern.matcher(text);
        
        while (matcher.find()) {
            emails.add(matcher.group());
        }
        
        return emails;
    }

}
