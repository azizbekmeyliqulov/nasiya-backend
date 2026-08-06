package xurshid_azizbek.com.example.nasiyabackend.mapper;

import org.springframework.stereotype.Component;
import xurshid_azizbek.com.example.nasiyabackend.entity.Payment;
import xurshid_azizbek.com.example.nasiyabackend.payload.response.PaymentResponse;

@Component
public class PaymentMapper {
    public PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getBefore(),
                payment.getAmount(),
                payment.getAfter(),
                payment.getCreatedAt()
        );
    }
}

