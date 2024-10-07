package com.SpringbootProject.ProjectManagementTool.Services;

import com.SpringbootProject.ProjectManagementTool.model.Enum.PlanType;
import com.SpringbootProject.ProjectManagementTool.model.Subscription;
import com.SpringbootProject.ProjectManagementTool.model.User;
import com.SpringbootProject.ProjectManagementTool.repository.SubscriptionRepository;
import com.razorpay.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionImpl implements SubscriptionService{

    @Autowired
    UserService userService;
    @Autowired
    SubscriptionRepository subscriptionRepository;
    @Override
    public Subscription createSubscription(User user) {
        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);
        Subscription savedSubscription = subscriptionRepository.save(subscription);
        return savedSubscription;
    }

    @Override
    public Subscription getUserSubscription(Long userId) {
//        System.out.println("User id = "+userId);
        Subscription subscription = subscriptionRepository.findByUserId(userId);
//        System.out.println("subscription = " + subscription);
        if(!isValid(subscription)){
            subscription.setPlanType(PlanType.FREE);
            subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
            subscription.setSubscriptionStartDate(LocalDate.now());
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription upgradeSubscription(Long userId, PlanType planType) {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
//        System.out.println("upgraded subscription : userId and planType subscription : "+userId+" "+planType+" "+subscription);

            subscription.setPlanType(planType);
            subscription.setSubscriptionStartDate(LocalDate.now());
            if (planType.equals(PlanType.ANNUALLY)) {
                subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(12));
            } else {
                subscription.setSubscriptionEndDate(LocalDate.now().plusMonths(1));
            }
            Subscription savedSubscription = subscriptionRepository.save(subscription);
//            System.out.println("Subscription saved in  database : " + savedSubscription);

        return savedSubscription;
    }

    @Override
    public boolean isValid(Subscription subscription) {

//        System.out.println("subscription : "+subscription.toString());
        if(subscription.getPlanType().equals(PlanType.FREE)){
            return true;
        }
        LocalDate endDate=subscription.getSubscriptionEndDate();
//        LocalDate startDate=subscription.getSubscriptionStartDate();

        LocalDate currentDate=LocalDate.now();
        return endDate.isAfter(currentDate) || endDate.isEqual(currentDate);
    }

}
