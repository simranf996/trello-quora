package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthTokenEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService {

    @Autowired
    UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity deleteUser(final String accessToken, final String userId) throws AuthorizationFailedException, UserNotFoundException {

        UserEntity userEntityToDelete = userDao.getUser(userId);

        UserAuthTokenEntity userAuthTokenEntity = userDao.getUserAuthToken(accessToken);
        UserEntity currentUserEntity = userAuthTokenEntity.getUser();

        if(!currentUserEntity.getRole().equalsIgnoreCase("admin"))
            throw new AuthorizationFailedException("ATHR-003", "Unauthorized Access, Entered user is not an admin");

        if(userEntityToDelete == null)
            throw new UserNotFoundException("USR-001", "User with entered uuid to be deleted does not exist");

        if(userAuthTokenEntity==null)
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");

        if(userAuthTokenEntity.getLogoutAt()!=null)
            throw new AuthorizationFailedException("ATHR-002", "User is signed out");

        userDao.deleteUser(userEntityToDelete);

        return userEntityToDelete;
    }
}
