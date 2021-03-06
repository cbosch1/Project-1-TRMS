package TRMS.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import TRMS.pojos.Employee;
import TRMS.pojos.User;


public class AuthServiceImpl implements AuthService {

    private static Algorithm algorithmT = Algorithm.HMAC256("enVariable");

    @Override
    public boolean authenticateUser(User user) {
        return true;
    }

    @Override
    public String createToken(User user, Employee emp) {
        String token = "";
        try {
            token = JWT.create()
            .withIssuer("TRMS-CB")
            .withClaim("name", emp.getName())
            .withClaim("id", user.getUserId())
            .withClaim("emp", user.getEmployeeId())
            .withClaim("privilege", user.getPrivilege().toString())
            .sign(algorithmT);
        
        } catch (JWTCreationException e) {
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
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    @Override
    public String readTokenName(String token) {
        String name = "";
        try {
            JWTVerifier verifier = JWT.require(algorithmT).withIssuer("TRMS-CB").build();
            name = verifier.verify(token).getClaim("name").asString();
            return name;
        } catch (JWTVerificationException e){
            return name;
        } catch (NullPointerException e) {
            return name;
        }
    }

    @Override
    public int readTokenId(String token) {
        int id = 0;
        try {
            JWTVerifier verifier = JWT.require(algorithmT).withIssuer("TRMS-CB").build();
            id = verifier.verify(token).getClaim("id").asInt();
            return id;
        } catch (JWTVerificationException e){
            return id;
        } catch (NullPointerException e) {
            return id;
        }
    }

    @Override
    public int readTokenEmp(String token) {
        int emp = 0;
        try {
            JWTVerifier verifier = JWT.require(algorithmT).withIssuer("TRMS-CB").build();
            emp = verifier.verify(token).getClaim("emp").asInt();
            return emp;
        } catch (JWTVerificationException e){
            return emp;
        } catch (NullPointerException e) {
            return emp;
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
            return "";
        } catch (NullPointerException e) {
            return "";
        }
    }
}
