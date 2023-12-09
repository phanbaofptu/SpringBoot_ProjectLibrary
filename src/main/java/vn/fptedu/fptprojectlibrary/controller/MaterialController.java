package vn.fptedu.fptprojectlibrary.controller;


import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.fptedu.fptprojectlibrary.model.Material;
import vn.fptedu.fptprojectlibrary.model.User;
import vn.fptedu.fptprojectlibrary.repository.MaterialRepository;
import vn.fptedu.fptprojectlibrary.repository.RoleRepository;
import vn.fptedu.fptprojectlibrary.repository.UserRepository;
import vn.fptedu.fptprojectlibrary.service.StorageFileNotFoundException;
import vn.fptedu.fptprojectlibrary.service.StorageService;
import vn.fptedu.fptprojectlibrary.service.UserService;

import java.util.List;

@Controller
public class MaterialController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    private final StorageService storageService;

    @Autowired
    public MaterialController(StorageService storageService) {
        this.storageService = storageService;
    }




    @GetMapping("/teacher/material-manager")
    public String materialList(Model model) {
        List<Material> materialList = materialRepository.findAll();
        model.addAttribute("materialList", materialList);
        return "material-list";
    }
    @GetMapping("/teacher/material-add")
    public String add(Model model) {
        Material material = new Material();

        model.addAttribute("material", material);
        return "material-add";
    }

    @PostMapping("/teacher/material-add")
    public String addMaterial(@Valid Material material,   BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "material-add";
        }
        material.setName(material.getName().trim());

        User userLogin = userService.userLogin();
        material.setPostBy(userLogin.getId());
        materialRepository.save(material);

        return "redirect:/teacher/material-add?success";
    }

    @GetMapping("/teacher/material-update/id={id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid material Id:" + id));
        model.addAttribute("material", material);
        return "material-update";
    }

    @PostMapping("/teacher/material-update/id={id}")
    public String updateMaterial(@PathVariable("id") long id, @Valid Material material,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            material.setId(id);
            return "material-update";
        }
        materialRepository.save(material);
        return "redirect:/teacher/material-update/id="+id+"?success";
    }

    @GetMapping("/teacher/material-delete/id={id}")
    public String delete(@PathVariable("id") long id, Model model) {

        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid material Id:" + id));
        materialRepository.delete(material);
        return "redirect:/teacher/material-manager";
    }

    @GetMapping("/student/material-list")
    public String materialListStudent(Model model) {
        List<Material> materialList = materialRepository.findAll();
        model.addAttribute("materialList", materialList);
        return "student-material-list";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/teacher/material-upload-file")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,@Valid Material material,
                                   Model model) {

        storageService.store(file);
        model.addAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        material.setFileName(file.getOriginalFilename());
        material.setFileUrl("/files/"+file.getOriginalFilename());
        model.addAttribute("material",material);


        return "material-add";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }



}
