package com.example.demo.infrastructure.mapper;

import com.example.demo.core.Admin.model.response.ChatUserResponse;
import com.example.demo.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-09-19T23:26:58+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class ChatUserRespMapperImpl implements ChatUserRespMapper {

    @Override
    public User toEntity(ChatUserResponse dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( dto.getId() );
        user.ten( dto.getTen() );
        user.userName( dto.getUserName() );

        return user.build();
    }

    @Override
    public ChatUserResponse toDto(User entity) {
        if ( entity == null ) {
            return null;
        }

        ChatUserResponse chatUserResponse = new ChatUserResponse();

        chatUserResponse.setId( entity.getId() );
        chatUserResponse.setTen( entity.getTen() );
        chatUserResponse.setUserName( entity.getUserName() );

        return chatUserResponse;
    }

    @Override
    public List<User> toEntity(List<ChatUserResponse> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( ChatUserResponse chatUserResponse : dtoList ) {
            list.add( toEntity( chatUserResponse ) );
        }

        return list;
    }

    @Override
    public List<ChatUserResponse> toDto(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ChatUserResponse> list = new ArrayList<ChatUserResponse>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDto( user ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(User entity, ChatUserResponse dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getTen() != null ) {
            entity.setTen( dto.getTen() );
        }
        if ( dto.getUserName() != null ) {
            entity.setUserName( dto.getUserName() );
        }
    }
}
