package jp.sobue.slide.controller;

import java.io.File;
import java.util.List;
import jp.sobue.slide.entity.MarkdownDocument;
import jp.sobue.slide.entity.UploadFile;
import jp.sobue.slide.service.FileUploadService;
import jp.sobue.slide.service.MarkdownSlideService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class MarkdownSlideController {

  private final FileUploadService fileUploadService;

  private final MarkdownSlideService markdownSlideService;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String root() {
    return "redirect:/index";
  }

  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String index(Model model) {
    model.addAttribute("title", "Top Page");
    return "index";
  }

  @RequestMapping(value = "/upload", method = RequestMethod.GET)
  public String upload(Model model) {
    model.addAttribute("title", "Upload Page");
    return "upload";
  }

  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public String uploadPost(UploadFile uploadFile) {
    fileUploadService.upload(uploadFile.getFileName(), uploadFile.getFile());
    return "redirect:/view/" + uploadFile.getFileName();
  }

  @RequestMapping(value = "/view/{file}", method = RequestMethod.GET)
  public String viewTop(@PathVariable String file) {
    return "redirect:/view/" + file + "/1";
  }

  @RequestMapping(value = "/view/{file}/{page}", method = RequestMethod.GET)
  public String view(Model model, @PathVariable String file, @PathVariable Integer page) {
    List<MarkdownDocument> documents = markdownSlideService.get(new File(file + ".md"));

    if (page != 1) {
      model.addAttribute("backUrl", "/view/" + file + "/" + Integer.valueOf(page - 1).toString());
    } else {
      model.addAttribute("backUrl", "/index");
    }
    model.addAttribute("forwardUrl", "/view/" + file + "/" + Integer.valueOf(page + 1).toString());

    if (page > documents.size()) {
      model.addAttribute("title", "End of Presentation");
      return "eop";
    }

    model.addAttribute("title", "View Page");
    model.addAttribute("body", documents.get(page - 1).getBody());

    return "view";
  }
}
