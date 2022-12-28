package tech.hiphone.cms.web.rest;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tech.hiphone.cms.constants.MaterialType;
import tech.hiphone.cms.domain.Material;
import tech.hiphone.cms.repository.MaterialRepository;
import tech.hiphone.cms.service.PathService;
import tech.hiphone.cms.utils.QETag;
import tech.hiphone.cms.utils.VideoUtil;
import tech.hiphone.cms.web.vm.MaterialVM;
import tech.hiphone.commons.constants.AuthoritiesConstants;
import tech.hiphone.framework.utils.FileUtil;
import tech.hiphone.framework.web.util.ResponseUtil;;

@RestController
public class MaterialResource {

    private static final Logger log = LoggerFactory.getLogger(MaterialResource.class);

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private PathService pathService;

    @PostMapping("/api/materials")
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({ AuthoritiesConstants.MANAGER })
    public List<String> createMaterials(MaterialVM materialVM) {
        List<String> paths = new ArrayList<>();

        MultipartFile[] files = materialVM.getFile();
        if (!ObjectUtils.isEmpty(files)) {
            String materialWebPath = pathService.getWebPath() + "/resource/material/";
            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                String suffix = FileUtil.getSuffix(originalFilename);
                String originalame = FileUtil.getNameWithNoSuffix(originalFilename);
                try {
                    File tmpFile = File.createTempFile("cms/", "." + suffix);
                    FileUtil.writeFile(file.getInputStream(), tmpFile);

                    String hash = QETag.calcETag(tmpFile);
                    String path = today() + "/" + hash;

                    Material material = materialRepository.findById(path).orElseGet(() -> {
                        Material newMaterial = materialVM.toMaterial();
                        newMaterial.setType(checkExt(suffix));
                        newMaterial.setName(originalame);
                        newMaterial.setFileExt(suffix);
                        return newMaterial;
                    });

                    File dst = new File(pathService.getLocalPath() + "/" + path);
                    if ("mp4".equals(suffix)) {
                        File moveFlagFile = File.createTempFile("cms/", "." + suffix);
                        VideoUtil.movflags(tmpFile, moveFlagFile);
                        FileUtil.move(moveFlagFile, dst);
                    } else {
                        FileUtil.move(tmpFile, dst);
                    }
                    materialRepository.save(material);
                    // paths.add(new PathVM(materialWebPath + path + "." + suffix));
                    paths.add(materialWebPath + path);
                } catch (Exception e) {
                    e.printStackTrace();
                    paths.add(null);
                }
            }
        }
        return paths;
    }

    @GetMapping("/resource/material/{id}")
    public void getMaterial(HttpServletResponse response, @PathVariable("id") String id) {
        Material material = materialRepository.findById(id).orElseThrow();
        if (!material.isPublic()) {
            throw new AccessDeniedException("File is not public");
        }
        String fileName = pathService.getLocalPath() + "/" + id;
        ResponseUtil.output(new File(fileName), response);
    }

    @GetMapping("/api/materials")
    @Secured({ AuthoritiesConstants.MANAGER })
    public Page<Material> getMaterials(Pageable pageable) {
        return materialRepository.findAll(pageable);
    }

    @DeleteMapping("/api/material/{id}")
    @Secured({ AuthoritiesConstants.MANAGER })
    public void deleteMaterial(@PathVariable("id") String id) {
        log.info("Request to deleteMaterial {}", id);
        File file = new File(pathService.getLocalPath() + "/" + id);
        file.deleteOnExit();
        materialRepository.deleteById(id);
    }

    public static String checkExt(String ext) {
        if (StringUtils.isEmpty(ext)) {
            return MaterialType.OTHER;
        }

        String lext = ext.toLowerCase();
        switch (lext) {
        case "bmp":
        case "svg":
        case "png":
        case "gif":
        case "jpg":
        case "jpeg":
            return MaterialType.IMAGE;
        case "avi":
        case "mov":
        case "rmvb":
        case "rm":
        case "flv":
        case "mp4":
        case "3gp":
            return MaterialType.VIDEO;
        }
        return MaterialType.OTHER;
    }

    private static String today() {
        return LocalDate.now().format(formatter);
    }

}
