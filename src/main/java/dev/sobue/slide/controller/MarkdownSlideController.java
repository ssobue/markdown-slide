package dev.sobue.slide.controller;

import dev.sobue.slide.service.FileUploadService;
import dev.sobue.slide.service.MarkdownSlideService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UncheckedIOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * markdown-slide controller.
 *
 * @author SOBUE Sho
 */
@Controller
@RequiredArgsConstructor
public class MarkdownSlideController {

  private static final String TITLE_ATTRIBUTE = "title";

  /**
   * File uploading.
   */
  private final FileUploadService fileUploadService;

  /**
   * Markdown-slide logic.
   */
  private final MarkdownSlideService markdownSlideService;

  @RequestMapping(value = "/")
  public String root() {
    return "redirect:/index";
  }

  @RequestMapping(value = "/index")
  public String index(Model model) {
    model.addAttribute(TITLE_ATTRIBUTE, "Top Page");
    return "index";
  }

  @GetMapping(value = "/upload")
  public String upload(Model model) {
    model.addAttribute(TITLE_ATTRIBUTE, "Upload Page");
    return "upload";
  }

  @PostMapping(
      value = "/upload",
      consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
  public String uploadPost(
      @RequestParam("fileName") String name, @RequestParam("file") MultipartFile multipartFile) {
    try {
      fileUploadService.upload(name, multipartFile.getInputStream());
    } catch (FileNotFoundException notFoundException) {
      throw new IllegalArgumentException(notFoundException);
    } catch (IOException ioException) {
      throw new UncheckedIOException(ioException);
    }

    return "redirect:/view/" + name;
  }

  @RequestMapping(value = "/view/{file}")
  public String viewTop(@PathVariable String file) {
    return "redirect:/view/" + file + "/1";
  }

  @RequestMapping(value = "/view/{file}/{page}")
  public String view(Model model, @PathVariable String file, @PathVariable Integer page) {
    var documents = markdownSlideService.get(new File(file + ".md"));

    if (page != 1) {
      model.addAttribute("backUrl", "/view/" + file + "/" + (page - 1));
    } else {
      model.addAttribute("backUrl", "/index");
    }
    model.addAttribute("forwardUrl", "/view/" + file + "/" + (page + 1));

    if (page > documents.size()) {
      model.addAttribute(TITLE_ATTRIBUTE, "End of Presentation");
      return "eop";
    }

    model.addAttribute(TITLE_ATTRIBUTE, "View Page");
    model.addAttribute("body", documents.get(page - 1).getBody());

    return "view";
  }
}
