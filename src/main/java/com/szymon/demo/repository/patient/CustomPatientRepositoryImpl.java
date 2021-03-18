package com.szymon.demo.repository.patient;

import com.mongodb.lang.Nullable;
import com.szymon.demo.collections.Patient;
import com.szymon.demo.collections.Photo;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public class CustomPatientRepositoryImpl implements CustomPatientRepository {

    private final MongoTemplate mongoTemplate;

    public CustomPatientRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @SneakyThrows
    @Override
    public boolean updateImageProfile(String title, MultipartFile image, @Nullable Long timestamp) {

        Patient patient = mongoTemplate.findOne(
                Query.query(Criteria.where("email").is(title)), Patient.class);

        if (patient == null) {
            return false;
        }

        patient.setProfileImage(new Photo(title, new Binary(BsonBinarySubType.BINARY, image.getBytes()), FilenameUtils.getExtension(image.getOriginalFilename()), timestamp));

        mongoTemplate.save(patient, "Patient");

        return true;
    }
}
