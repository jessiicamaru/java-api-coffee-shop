package com.example.coffeeshop.service.impl;

import com.example.coffeeshop.constants.Operator;
import com.example.coffeeshop.dto.PromotionConditionDto;
import com.example.coffeeshop.dto.PromotionDiscountDto;
import com.example.coffeeshop.dto.PromotionDto;
import com.example.coffeeshop.dto.PromotionEligibleUserDto;
import com.example.coffeeshop.model.FilterPromotionRequest;
import com.example.coffeeshop.model.Promotion;
import com.example.coffeeshop.model.PromotionEligibleUser;
import com.example.coffeeshop.repository.PromotionRepository;
import com.example.coffeeshop.repository.PromotionUsageRepository;
import com.example.coffeeshop.repository.UserOrderRepository;
import com.example.coffeeshop.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private PromotionUsageRepository promotionUsageRepository;
    @Autowired
    private UserOrderRepository userOrderRepository;


    @Override
    public List<PromotionDto> findAll() {
        return promotionRepository.findAll().stream().map(promotion -> mapToPromotionDto(promotion)).collect(Collectors.toList());
    }


    public List<String> filterPromotions(FilterPromotionRequest request) {
        if (request.getUid() == null || request.getUid().trim().isEmpty()) {
            throw new IllegalArgumentException("UID is required");
        }

        int orderCount = userOrderRepository.countOrdersByUser(request.getUid());
        List<Promotion> activePromotions = promotionRepository.findActivePromotions(LocalDateTime.now());
        List<String> invalidPromotionIds = new ArrayList<>();

        for (Promotion promotion : activePromotions) {
            int usageCount = promotionUsageRepository.countUsageByPromotionAndUser(promotion.getPromotionId(), request.getUid());
            if (promotion.getUsageLimitPerUser() > 0 && usageCount >= promotion.getUsageLimitPerUser()) {
                invalidPromotionIds.add(promotion.getPromotionId());
                continue;
            }

            boolean isEligible = true;
            for (PromotionEligibleUser criteria : promotion.getEligibleUsers()) {
                isEligible = checkEligibility(criteria, request.getUid(), orderCount);
                if (!isEligible) {
                    invalidPromotionIds.add(promotion.getPromotionId());
                    break;
                }
            }

            if (isEligible && !isPromotionConditionsMet(promotion, request)) {
                invalidPromotionIds.add(promotion.getPromotionId());
            }
        }

        return invalidPromotionIds;
    }


    private boolean isPromotionConditionsMet(Promotion promotion, FilterPromotionRequest request) {
        return promotion.getConditions().stream().allMatch(condition -> {
            switch (condition.getConditionType()) {
                case totalOrderValue: // So sánh với totalAmount
                    return compareValue(request.getTotalAmount(), condition.getConditionValue(), condition.getOperator());
                case distance: // So sánh với distance
                    return compareValue(request.getDistance(), condition.getConditionValue(), condition.getOperator());
                case productQuantity: // So sánh với itemCount
                    return compareValue(request.getItemCount().doubleValue(), condition.getConditionValue(), condition.getOperator());
                case shippingFee:
                case shippingFeePercentage:
                    // TODO: Nếu cần xử lý shippingFee hoặc shippingFeePercentage, thêm logic ở đây
                    return true; // Tạm thời bỏ qua nếu không có dữ liệu tương ứng
                default:
                    return true; // Điều kiện không xác định, mặc định hợp lệ
            }
        });
    }

    private boolean compareValue(Double value, Double threshold, Operator operator) {
        int comparison = Double.compare(value, threshold);
        switch (operator) {
            case greaterThan:
                return comparison > 0;
            case equal:
                return comparison == 0;
            case lessThan:
                return comparison < 0;
            default:
                return false;
        }
    }

    private PromotionDto mapToPromotionDto(Promotion promotion) {
        return PromotionDto.builder()
                .promotionId(promotion.getPromotionId())
                .code(promotion.getCode())
                .name(promotion.getName())
                .description(promotion.getDescription())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .status(promotion.getStatus())
                .promotionType(promotion.getPromotionType())
                .usageLimitPerUser(promotion.getUsageLimitPerUser())
                .conditions(promotion.getConditions().stream()
                        .map(condition -> PromotionConditionDto.builder()
                                .id(condition.getId())
                                .conditionType(condition.getConditionType())
                                .operator(condition.getOperator())
                                .conditionValue(condition.getConditionValue())
                                .build())
                        .collect(Collectors.toList()))
                .discounts(promotion.getDiscounts().stream()
                        .map(discount -> PromotionDiscountDto.builder()
                                .id(discount.getId())
                                .discountTarget(discount.getDiscountTarget())
                                .discountType(discount.getDiscountType())
                                .discountValue(discount.getDiscountValue())
                                .build())
                        .collect(Collectors.toList()))
                .eligibleUsers(promotion.getEligibleUsers().stream()
                        .map(eligibleUser -> PromotionEligibleUserDto.builder()
                                .id(eligibleUser.getId())
                                .criteriaType(eligibleUser.getCriteriaType())
                                .operator(eligibleUser.getOperator())
                                .criteriaValue(eligibleUser.getCriteriaValue())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private boolean checkEligibility(PromotionEligibleUser criteria, String uid, int orderCount) {
        switch (criteria.getCriteriaType()) {
            case orderCount:
                int requiredOrderCount = Integer.parseInt(criteria.getCriteriaValue());
                switch (criteria.getOperator()) {
                    case greaterThan:
                        return orderCount > requiredOrderCount;
                    case equal:
                        return orderCount == requiredOrderCount;
                    case lessThan:
                        return orderCount < requiredOrderCount;
                    default:
                        return false;
                }
            case newUser:
                boolean isNewUser = orderCount == 0;
                boolean criteriaValue = Boolean.parseBoolean(criteria.getCriteriaValue());
                return criteria.getOperator() == Operator.equal && isNewUser == criteriaValue;
            case accountAge:
                // TODO: Tính accountAge dựa trên ngày đăng ký người dùng (cần thêm bảng hoặc trường trong users)
                return true; // Giả lập, cần triển khai nếu có dữ liệu
            default:
                return false;
        }
    }
}