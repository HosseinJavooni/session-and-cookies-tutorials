package hossein.com.example.sessionandcookiestutorials.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hossein.com.example.sessionandcookiestutorials.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cookie")
public class CookieAndSessionController {

    @GetMapping
    public String goTologinPage() {
        return "login";
    }

    @PostMapping(path = "/userlogin", consumes = { "*/*" })
    public String userLogin(@RequestBody MultiValueMap user, HttpServletResponse response) {
        List usernameTemp = (List) user.get("username");
        String username = (String) usernameTemp.get(0);
        Cookie cookie = new Cookie("username", username);
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);
        return "resultPage";
    }

    @GetMapping("/userlogin")
    public String getCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie userCookie = null;
        String username = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("username"))
                userCookie = cookie;
        }
        if (userCookie != null) {
            username = userCookie.getValue();
        }
        System.out.println("username from cookie is: " + username);
        return "resultPage";
    }

    @GetMapping("/getCookieSpringBoot")
    public String getCookiesInSpringBoot(@CookieValue("username") String username) {
        System.out.println("username is: " + username);
        return "resultPage";
    }

    @GetMapping("/getSession")
    public String getSession(@RequestParam String firstName, @RequestParam String lastName,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.isNew()) {
            session.setAttribute("firstName", firstName);
            session.setAttribute("lastName", lastName);
            session.setAttribute("user", new User(firstName, lastName));
        }
        System.out.println("user is: " + ((User) session.getAttribute("user")).toString());
        System.out.println("FirstName is: " + session.getAttribute("firstName"));
        System.out.println("LastName is: " + session.getAttribute("lastName"));
        return "resultPage";
    }

}
