package    com.example.demo.service;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseStorageService {

    private final String bucketName = "pod-booking-system.appspot.com";

    public String uploadFile(MultipartFile file) throws IOException {
        Storage storage = StorageOptions.getDefaultInstance().getService();

        // Generate a unique filename
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        // Define the blob ID and blob info
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();

        // Upload the file to Firebase
        storage.create(blobInfo, file.getInputStream());

        // Generate the public URL to access the file
        return String.format("https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucketName, fileName);
    }
}
