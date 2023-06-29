package com.ogym.project.inquiry;

import com.ogym.project.DataNotFoundException;
import com.ogym.project.user.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    public Inquiry create(String title, String content, SiteUser inquirer) {

        Inquiry inquiry = new Inquiry();
        inquiry.setTitle(title);
        inquiry.setContent(content);
        inquiry.setInquirer(inquirer);
        inquiry.setCreateDate(LocalDateTime.now());

        this.inquiryRepository.save(inquiry);

        return inquiry;
    }

    public Inquiry getInquiry(Long id) {
        Optional<Inquiry> oi = this.inquiryRepository.findById(id);
        if (oi.isPresent()) {
            return oi.get();
        } else {
            throw new DataNotFoundException("inquiry not found");
        }
    }
}
