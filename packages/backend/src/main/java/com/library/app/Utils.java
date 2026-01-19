package com.library.app;

import com.library.app.domain.owner.Owner;
import com.library.app.domain.user.User;

public class Utils {
    public static boolean isOwner(User user) {
        return user instanceof Owner;
    }
}
