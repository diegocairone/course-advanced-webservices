package com.cairone.utils;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MediaTypeToFileExtensionUtilTest {

    @Test
    void givenJpegMediaType_whenGetExtension_thenJpegExtension() {
        String extension = MediaTypeToFileExtensionUtil.getExtension(MediaType.IMAGE_JPEG);
        assertEquals(".jpeg", extension);
    }

    @Test
    void givenPngMediaType_whenGetExtension_thenPngExtension() {
        String extension = MediaTypeToFileExtensionUtil.getExtension(MediaType.IMAGE_PNG);
        assertEquals(".png", extension);
    }

    @Test
    void givenGifMediaType_whenGetExtension_thenGifExtension() {
        String extension = MediaTypeToFileExtensionUtil.getExtension(MediaType.IMAGE_GIF);
        assertEquals(".gif", extension);
    }

    @Test
    void givenUnknownMediaType_whenGetExtension_thenDatExtension() {
        String extension = MediaTypeToFileExtensionUtil.getExtension(MediaType.APPLICATION_OCTET_STREAM);
        assertEquals(".dat", extension);
    }
}