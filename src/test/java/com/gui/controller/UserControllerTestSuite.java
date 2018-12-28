package com.gui.controller;

import com.gui.dto.UserDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class UserControllerTestSuite {

    private UserController userController;

    @Before
    public void setController() {
        userController = new UserController();
    }

    @After
    public void cleanUp() {


    }


    @Test
    public void testCreateAccount(){
        //given
        UserDto userDto = new UserDto("test", "test", "investment.portfolio.app@gmail.com");
        //when
        userController.createAccount(userDto);
        //then

    }

    @Test
    public void deleteAccount() {
        //given
        String response = userController.signIn("test", "test");
        System.out.println(response);
        //when

        //then


    }

}
