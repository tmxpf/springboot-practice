package bootmain.web;

import bootmain.config.auth.LoginUser;
import bootmain.config.auth.dto.SessionUser;
import bootmain.service.posts.PostsService;
import bootmain.web.dto.PostsListResponseDto;
import bootmain.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class DexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, HttpSession session, @LoginUser SessionUser user) {
//      SessionUser user = (SessionUser)session.getAttribute("user");

        try {

            List<PostsListResponseDto> result = postsService.findAllDesc();
            model.addAttribute("posts", result);

            getURLData();

            if (user != null) {
                model.addAttribute("userName", user.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("posts", dto);

        return "posts-update";
    }

    private void getURLData() throws Exception {
        URL url = new URL("https://www.naver.com/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String temp;
        int rank = 0;

        while ((temp = br.readLine()) != null) {
            if(temp.contains("class=\"ah_k\"") && rank < 20) {
                System.out.println(++rank + "위 : " + temp.split(">")[1].split("<")[0]);
            }

            if(temp.contains("property=\"og:image\"")) {
                System.out.println(temp.split("content=\"")[1].split("\">")[0]);
            }
        }
        con.disconnect();
        br.close();
    }

}
