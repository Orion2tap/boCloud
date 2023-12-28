package boGroup.boSSM.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class FileUploadController {
    // 本地
//    String dir = "avatar";
    // 服务器
    String dir = "/var/lib/tomcat9/webapps/images";

    @GetMapping("/upload/index")
    public ModelAndView uploadView() {
        return new ModelAndView("upload");
    }

    @PostMapping("/upload")
    @ResponseBody
//    public String singleFileUpload(@RequestParam("file") MultipartFile file) {
    public String singleFileUpload(@RequestParam MultipartFile file) {
            if (file.isEmpty()) {
            return "上传失败";
        } else {
            String path = "";
            // 本地 avatar/bobo.jpg
            // 服务器 /var/lib/tomcat9/webapps/images/bobo.jpg
            path = String.format("%s/%s", this.dir, file.getOriginalFilename());
            // 使用 FileOutputStream 将 file 的数据写入 path 对应的文件
            try(FileOutputStream os = new FileOutputStream(path)) {
                byte[] bytes = file.getBytes();
                os.write(bytes);;
                return "上传成功";
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败";
            }
        }
    }

    // 非 static 目录下的图片加载
    @GetMapping("/avatar/{imageName}")
    @ResponseBody
    // 因为图片是二进制数据 返回值类型为 ResponseEntity<byte[]>
    public ResponseEntity<byte[]> avatar(@PathVariable String imageName) {
        String path = String.format("%s/%s", dir, imageName);

        byte content[];
        try (FileInputStream is = new FileInputStream(path)) {
            content = is.readAllBytes();
        } catch (IOException e) {
            String s = String.format("Load file <%s> error <%s>", path, e);
            throw new RuntimeException(s);
        }

        // 通过链式调用设置: 1. 响应状态码 2. 文件类型 3. body
        return ResponseEntity.ok().
                contentType(MediaType.IMAGE_PNG).
                contentType(MediaType.IMAGE_JPEG).
                body(content);
    }
}
