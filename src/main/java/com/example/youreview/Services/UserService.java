package com.example.youreview.Services;

import com.example.youreview.Models.Dtos.SignedInUser;
import com.example.youreview.Models.Dtos.UserDTO;

public interface UserService {
    SignedInUser signUser(UserDTO user);
}
