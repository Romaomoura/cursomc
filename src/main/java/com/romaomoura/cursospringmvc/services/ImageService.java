package com.romaomoura.cursospringmvc.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.romaomoura.cursospringmvc.services.exceptions.FileException;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    public BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
        String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
        if (!"png".equals(ext) && !"jpg".equals(ext)) {
            throw new FileException("Somente imagens PNG ou JPG são permitidas");
        }
        try {

            BufferedImage img = ImageIO.read(uploadedFile.getInputStream());
            if ("jpg".equals(ext)) {
                img = jpgToPng(img);
            }
            return img;
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo!");
        }
    }

    // metodo para converte imagens jpg em PNG
    public BufferedImage jpgToPng(BufferedImage img) {
        BufferedImage pngImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        pngImg.createGraphics().drawImage(img, 0, 0, null);
        return pngImg;
    }

    // metodo para obter um inputStrem a partir de um BufferedImage
    public ByteArrayInputStream getInputStream(BufferedImage img, String extension) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(img, extension, os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo!");
        }
    }

    // recortar imagens para deixar em um padrão
    public BufferedImage cropSquare(BufferedImage sourceImg) {
        int min = (sourceImg.getHeight() <= sourceImg.getWidth()) ? sourceImg.getHeight() : sourceImg.getWidth();
        return Scalr.crop(sourceImg, (sourceImg.getWidth() / 2) - (min / 2), (sourceImg.getHeight() / 2) - (min / 2),
                min, min);

    }

    // redimensionar imagens
    public BufferedImage resize(BufferedImage sourceImg, int size) {
        return Scalr.resize(sourceImg, Scalr.Method.ULTRA_QUALITY, size);
    }
}