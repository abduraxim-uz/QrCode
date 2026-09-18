package uz.abduraxim.qrcode.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.abduraxim.MyLogger;

import java.awt.image.BufferedImage;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@Controller
@RequiredArgsConstructor
public class QrCodeController {

    @GetMapping(value = "/qr", produces = IMAGE_JPEG_VALUE)
    public ResponseEntity<BufferedImage> generateQRCode(@RequestParam String text) throws Exception {
        MyLogger.info("User text: " + text);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return ResponseEntity.ok(qrImage);
    }

    @GetMapping("/qr/start")
    public String generateQRCodePage(HttpServletRequest request, Model model) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int port = request.getServerPort();
        String contextPath = request.getContextPath();

        String baseUrl = scheme + "://" + serverName + ((port == 80 || port == 443) ? "" : ":" + port) + contextPath;
        MyLogger.info("baseUrl: " + baseUrl);
        model.addAttribute("baseUrl", baseUrl);
        return "qrcode-page";
    }
}