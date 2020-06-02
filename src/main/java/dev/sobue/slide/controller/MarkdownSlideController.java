package dev.sobue.slide.controller;

import dev.sobue.slide.entity.MarkdownDocument;
import dev.sobue.slide.entity.UploadFile;
import dev.sobue.slide.service.FileUploadService;
import dev.sobue.slide.service.MarkdownSlideService;
import java.io.File;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MarkdownSlideController {

  private final FileUploadService fileUploadService;

  private final MarkdownSlideService markdownSlideService;

  @GetMapping(value = "/")
  public String root() {
    return "redirect:/index";
  }

  @GetMapping(value = "/index")
  public String index(Model model) {
    model.addAttribute("title", "Top Page");
    return "index";
  }

  @GetMapping(value = "/upload")
  public String upload(Model model) {
    model.addAttribute("title", "Upload Page");
    return "upload";
  }

  @PostMapping(value = "/upload")
  public String uploadPost(UploadFile uploadFile) {
    fileUploadService.upload(uploadFile.getFileName(), uploadFile.getFile());
    return "redirect:/view/" + uploadFile.getFileName();
  }

  @GetMapping(value = "/view/{file}")
  public String viewTop(@PathVariable String file) {
    return "redirect:/view/" + file + "/1";
  }

  @GetMapping(value = "/view/{file}/{page}")
  public String view(Model model, @PathVariable String file, @PathVariable Integer page) {
    List<MarkdownDocument> documents = markdownSlideService.get(new File(file + ".md"));

    if (page != 1) {
      model.addAttribute("backUrl", "/view/" + file + "/" + (page - 1));
    } else {
      model.addAttribute("backUrl", "/index");
    }
    model.addAttribute("forwardUrl", "/view/" + file + "/" + (page + 1));

    if (page > documents.size()) {
      model.addAttribute("title", "End of Presentation");
      return "eop";
    }

    model.addAttribute("title", "View Page");
    model.addAttribute("body", documents.get(page - 1).getBody());

    return "view";
  }
}
