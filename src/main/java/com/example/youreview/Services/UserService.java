package com.example.youreview.Services;

import com.example.youreview.Models.Dtos.SignedInUser;
import com.example.youreview.Models.Dtos.UserAuthDTO;

public interface UserService {
    SignedInUser signUser(UserAuthDTO user);
}
