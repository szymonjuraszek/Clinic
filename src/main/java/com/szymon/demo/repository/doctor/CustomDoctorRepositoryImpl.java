package com.szymon.demo.repository.doctor;

import com.mongodb.lang.Nullable;
import com.szymon.demo.collections.Doctor;
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
public class CustomDoctorRepositoryImpl implements CustomDoctorRepository {

    private final MongoTemplate mongoTemplate;

    public CustomDoctorRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @SneakyThrows
    @Override
    public boolean updateImageProfile(String title, MultipartFile image, @Nullable Long timestamp) {

        Doctor doctor = mongoTemplate.findOne(
                Query.query(Criteria.where("email").is(title)), Doctor.class);

        if (doctor == null) {
            return false;
        }

        doctor.setProfileImage(new Photo(title, new Binary(BsonBinarySubType.BINARY, image.getBytes()), FilenameUtils.getExtension(image.getOriginalFilename().toLowerCase()), timestamp));

        mongoTemplate.save(doctor, "Doctor");

        return true;
    }

}
