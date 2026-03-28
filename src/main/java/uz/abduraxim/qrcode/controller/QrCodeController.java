package uz.abduraxim.qrcode.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uz.abduraxim.MyLogger;
import uz.abduraxim.qrcode.util.StartUp;

import java.awt.image.BufferedImage;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@Controller
@RequiredArgsConstructor
public class QrCodeController {

    private final StartUp startup;

    @GetMapping(value = "/qr/{text}", produces = IMAGE_JPEG_VALUE)
    public ResponseEntity<BufferedImage> generateQRCode(@PathVariable String text) throws Exception {
        MyLogger.info("User text: " + text);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 200, 200);
        BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
        return ResponseEntity.ok(qrImage);
    }

    @GetMapping("/qr/start")
    public String generateQRCodePage(Model model) {
        model.addAttribute("baseUrl", "http://localhost:" + startup.getServerPort());
        return "qrcode-page";
    }
}