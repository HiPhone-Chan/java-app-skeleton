package tech.hiphone.framework.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import tech.hiphone.framework.utils.FileUtil;

public interface ResponseUtil {

    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
        return wrapOrNotFound(maybeResponse, null);
    }

    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse, HttpHeaders header) {
        return maybeResponse.map(response -> ResponseEntity.ok().headers(header).body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    static <X> ResponseEntity<List<X>> wrapPage(Page<X> page) {
        HttpHeaders headers = PaginationUtil
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    // let browser determine to open
    static void outputMedia(File file, HttpServletResponse response) {
        MediaType mediaType = getMediaType(file.getName());
        response.setContentType(mediaType.toString());
        output(file, response);
    }

    // just download
    static void download(File file, HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
        output(file, response);
    }

    static void output(File file, HttpServletResponse response) {
        try (OutputStream os = response.getOutputStream(); InputStream is = new FileInputStream(file);) {
            IOUtils.copy(is, os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static MediaType getMediaType(String fileName) {
        String suffix = FileUtil.getSuffix(fileName);
        if (StringUtils.isNotEmpty(suffix)) {
            switch (suffix) {
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "webp":
                return new MediaType("image", "webp");
            case "pdf":
                return MediaType.APPLICATION_PDF;
            case "md":
                return MediaType.TEXT_MARKDOWN;
            case "mp4":
                return MediaType.valueOf("video/mp4");
            }
        }
        return MediaType.ALL;
    }
}
