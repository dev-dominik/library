package com.library.app.utils;

import com.library.app.domain.owner.Owner;
import com.library.app.domain.user.User;

public class Permissions {
    public static boolean isOwner(User user) {
        return user instanceof Owner;
    }
}
