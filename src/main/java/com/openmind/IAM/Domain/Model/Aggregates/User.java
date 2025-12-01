package com.openmind.IAM.Domain.Model.Aggregates;

import com.openmind.IAM.Domain.Model.ValueObjects.Email;
import com.openmind.IAM.Domain.Model.ValueObjects.HashedPassword;
import com.openmind.IAM.Domain.Model.ValueObjects.UserId;
import com.openmind.IAM.Domain.Services.PasswordEncoder;
import lombok.Getter;

@Getter
public class User {
    private final UserId id;
    private final Email email;
    private final HashedPassword password;
    private final String name;

    private User(Builder builder){
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
        this.name = builder.name;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private UserId id;
        private Email email;
        private HashedPassword password;
        private String name;

        public Builder id(Long id){
            this.id = id != null? new UserId(id): null;
            return this;
        }
        public Builder email(String email){
            this.email = new Email(email);
            return this;
        }
        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder hashedPassword(String password){
            this.password = new HashedPassword(password);
            return this;
        }

        public Builder password(String raw, PasswordEncoder encoder){
            this.password = encoder.encode(raw);
            return this;
        }

        public User build(){
            return new User(this);
        }
    }
}
