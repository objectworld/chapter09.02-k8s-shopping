package org.objectworld.shopping.order.domain;

import org.objectworld.shopping.common.domain.AbstractEntity;
import org.objectworld.shopping.order.domain.enumeration.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * A Payment.
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"paypalPaymentId"}, callSuper=false)
@ToString(callSuper=true)
@Entity
@SequenceGenerator(
		name="payment_seq_gen",
		sequenceName="payment_seq",
		initialValue=1,
		allocationSize=1
		)
@Table(name = "payment")
public class Payment extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "paypal_payment_id")
    private String paypalPaymentId;

    @Id
    @GeneratedValue(
    		strategy = GenerationType.SEQUENCE,
    		generator = "payment_seq_gen"
    )
    private Long id;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id", unique = true)
    @ToString.Exclude	
    private Order order;

    @ToString.Include
    public Long orderId() {
    	return order.getId();
    }

    @Builder
    public Payment(String paypalPaymentId, @NotNull PaymentStatus status, Order order) {
        this.paypalPaymentId = paypalPaymentId;
        this.status = status;
        this.order = order;
    }
}
