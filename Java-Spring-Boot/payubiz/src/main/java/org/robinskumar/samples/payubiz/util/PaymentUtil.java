package org.robinskumar.samples.payubiz.util;

import org.springframework.stereotype.Component;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;

@Component
public class PaymentUtil {

    /*
    * sha512(key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5||||||SALT)
    * hash = sha512(gtKFFx|PLS-999994w5-3|600.000|SAU Admission 2014|Robin|robinsxxxxx@xxxx.com|||||||||||eCwWELxi)
    * */
    public String hashCal(String type,String str){
        byte[] hashseq=str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try{
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();

            for (int i=0;i<messageDigest.length;i++) {
                String hex=Integer.toHexString(0xFF & messageDigest[i]);
                if(hex.length()==1) hexString.append("0");
                hexString.append(hex);
            }

        }catch(NoSuchAlgorithmException nsae){ }

        return hexString.toString();
    }

    public boolean empty(String s)
    {
        if(s== null || s.trim().equals(""))
            return true;
        else
            return false;
    }

    public String generateHashString(Map<String,String> params)
    {
        /*
         * In udf1-udf10 you can pass any data user define like
         * Address1
         * Address2
         * City
         * State
         * Country
         * Zip-code
         * */
        String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10|SALT";
        //sha512(key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5||||||SALT)

        String[] hashSequenceStrings = hashSequence.split("\\|");
        String hashString = "";
        for(String part : hashSequenceStrings)
        {
            hashString= (empty(params.get(part)))?hashString.concat(""):hashString.concat(params.get(part));
            if(!part.equalsIgnoreCase("SALT")){
                hashString=hashString.concat("|");
            }
        }
        return hashString;
    }

    public String generateTxnId()
    {
        Random rand = new Random();
        String txnId = Integer.toString(rand.nextInt())+(System.currentTimeMillis() / 1000L);
        return hashCal("SHA-256",txnId).substring(0,20);
    }

    public void validate(Map<String,String> params) throws Exception{
        if( empty(params.get("key"))
                || empty(params.get("txnid"))
                || empty(params.get("amount"))
                || empty(params.get("firstname"))
                || empty(params.get("email"))
                || empty(params.get("phone"))
                || empty(params.get("productinfo"))

                || empty(params.get("surl"))
                || empty(params.get("furl"))
                || empty(params.get("curl")))
            throw  new  Exception();
    }
}
