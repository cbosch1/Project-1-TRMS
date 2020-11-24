package TRMS.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import TRMS.pojos.User;


public class AuthServiceImpl implements AuthService {

    private static Algorithm algorithmT = Algorithm.HMAC256("enVariable");

    @Override
    public boolean authenticateUser(User user) {
        return true;
    }

    @Override
    public String createToken(User user) {
        String token = "";
        try {
            token = JWT.create()
            .withIssuer("TRMS-CB")
            .withClaim("name", user.getUsername())
            .withClaim("id", user.getUserId())
            .withClaim("privilege", user.getPrivilege().toString())
            .sign(algorithmT);
        
        } catch (JWTCreationException e) {
            e.printStackTrace();
        }
        return token;
    }

    @Override
    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithmT).withIssuer("TRMS-CB").build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e){
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readTokenPrivilege(String token) {
        String priv = "";
        try {
            JWTVerifier verifier = JWT.require(algorithmT).withIssuer("TRMS-CB").build();
            priv = verifier.verify(token).getClaim("privilege").asString();
            return priv;
        } catch (JWTVerificationException e){
            e.printStackTrace();
            return "";
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        }
    }
}
