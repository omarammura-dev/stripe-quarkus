package com.stripedemo.quarkus;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.annotations.Pos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Path("/stripe")
@ApplicationScoped
public class StripeService {

    String secretKey = "sk_test_34NXfFalPoP________________________________";
    String priceId = "price_1OHNc_____________";


    @Path("/create_session")
    @POST
    public Session createStripeTransaction(){
        Stripe.apiKey = secretKey;
        List<HashMap> list = new ArrayList<>();
        HashMap<String,Object> hashMap = new HashMap<>();
       hashMap.put("price",priceId);
       hashMap.put("quantity", 1);
       list.add(hashMap);
       HashMap<String, Object> params = new HashMap<>();
       params.put("success_url", "https://example.come");
       params.put("line_items", list);
       params.put("mode", "payment");

        try {
            Session session = Session.create(params);
            return session;
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    @Path("/get_session")
    public Session getSession(@QueryParam("session_id") String sessionId){
      try {
          return Session.retrieve(sessionId);
      }catch (StripeException e){
          throw new RuntimeException(e);
      }
    }

    @Path("/get_payment_status")
    public String getPaymentStatus(Session session){
            String paymentIntent = session.getPaymentStatus();
            return paymentIntent;
    }
}


