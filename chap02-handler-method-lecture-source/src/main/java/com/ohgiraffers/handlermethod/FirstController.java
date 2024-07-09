package com.ohgiraffers.handlermethod;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Controller
@RequestMapping("/first/*")
@SessionAttributes("id")
public class FirstController {

    /*
    * 컨트롤러 핸들러 메서도의 반환 값을 void로 설정하면 요청 주소가 view의 이름이 된다.
    * => /first/regist 요청이 들어오면 /first/regist 뷰를 응답한다.
    * */

    // 페이지 이동용 핸들러 메소드
    @GetMapping("regist")
    public void regist(){}

    /*
    * WebRequest로 요청 파라미터 전달 받기
    * 파랄미터 선언부에 WebRequest 타입을 선언하면 해당 메소드 호출 시 인자로 값을 전달해준다.
    * HttpServletRequest, HttpservletResponse / servletRequest,ServletResponse 가능하다.
    * */
    @PostMapping("regist")
    public String registMenu(Model model, WebRequest request) {

        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));
        int categoryCode = Integer.parseInt(request.getParameter("categoryCode"));

        System.out.println("name = " + name);
        System.out.println("price = " + price);
        System.out.println("categoryCode = " + categoryCode);

        String message = name + "을(를) 신규 메뉴 목록의 " + categoryCode + "번 카테고리에 "
                + price + "원으로 등록 하셨습니다.";

        System.out.println("message = " + message);

        model.addAttribute("message",message);

        return "first/messagePrinter";
    }

    /*
    * @RequestParam으로 요청 파라미터 전달 받기
    * 요청 파라미터를 매핑해 호출 시 값을 넣어주는 어노테이션으로 매개변수 앞에 작성한다.
    * form의 name 속성값과 매개변수의 이름이 다른 경우 @RequestParam("이름") 설정하면 된다.
    *
    * 생략이 가능하다. 하지만 가독성을 위해서 써주는게 좋다.
    *
    * required속성 : 값이 들어오지 않아도 400 에러를 발생시키지 않는다. (default = true)
    * defaultValue : 기본값을 설정
    * */

    @GetMapping("modify")
    public void modify(){}

    @PostMapping("modify")
    public String modifyMenuPrice(Model model, @RequestParam(required = false) String modifyName,
                                  @RequestParam(defaultValue = "0") int modifyPrice) {

        String message = modifyName + " 메뉴의 가격을 " + modifyPrice + "원으로 가격을 변경하였습니다.";
        System.out.println("message = " + message);

        model.addAttribute("message", message);

        return "first/messagePrinter";

    }

    /*
    * 파라미터가 여러개인 경우 Map으로 한번에 처리할 수 있다.
    * 이때, Map 의 키는 form의 name 속성값이 된다.
    * */

    @PostMapping("modifyAll")
    public String modifyMenu(Model model, @RequestParam Map<String, String> parameters) {

        String modifyName = parameters.get("modifyName2");
        int modifyPrice = Integer.parseInt(parameters.get("modifyPrice2"));

        String message = modifyName + " 메뉴의 가격을 " + modifyPrice + "원으로 가격을 변경하였습니다.";
        System.out.println("message = " + message);

        model.addAttribute("message", message);

        return "first/messagePrinter";
    }

    // first/search 로 페이지 이동
    @GetMapping("search")
    public void search(){}

    /*
    * @ModelAttribute
    * - DTO 같은 모델을 커맨드 객체로 전달받는다.
    *
    * @ModelAttribute의 경우 커맨드 객체를 생성하여 매개변수로 전달 해 준 뒤 인스턴스를 model에 담는다.
    * 경우에 따라 폼에서 입력한 값을 다음화면으로 바로 전달해야 할 경우가 발생하는데, 이때 유용하게 사용된다.
    * @ModelAttribute("모델에담을Key값")을 지정 할 수 있으며
    * 지정하지 않으면 타입의 앞글자를 소문자로 한 네이밍 규칙을 따른다.
    *  -> menuDTO
    *
    * 해당 어노테이션은 생략이 가능하지만 명시적으로 작성하는 것이 좋다.
    * 이때 주의할점은 DTO와 입력받을 폼을 잘 맞춰줘야 한다.
    * */

    @PostMapping("search")
    public String searchMenu(@ModelAttribute MenuDTO menu) {

        System.out.println("menu = " + menu);

        return "first/searchResult";
    }

    @GetMapping("login")
    public void login(){}

    /*
    * 4-1 session 이용하기
    * HttpSession을 매개변수로 선언하면 핸들러 메소드 호출 시 세션 객체를 넣어서 호출한다.
    * */

    @PostMapping("login1")
    public String sessionTest1 (HttpSession session, @RequestParam String id) {

        System.out.println("session = " + session);
        System.out.println("id = " + id);

        session.setAttribute("id",id);

        return "first/loginResult";
    }

    @GetMapping("logout1")
    public String logoutTest1(HttpSession session) {

        session.invalidate();

        return "first/loginResult";
    }


    /*
    * @SessionAttributes를 이용하여 session에 값 담기
    * 클래스레벨에 @SessionAttributes 어노테이션을 이용하여 세션에 담을 key 값을 설정
    * Model 영역에 해당 key로 값이 추가되는 경우 session에 자동으로 등록해준다.
    * */
    @PostMapping("login2")
    public String sessionTest2(Model model, @RequestParam String id) {

        model.addAttribute("id",id);

        return "first/loginResult";
    }

    @GetMapping("logout2")
    public String logoutTest2(SessionStatus sessionStatus) {

        // 현재 컨트롤러 세션에 저장된 모든 정보를 제거
        // 개별 제거가 불가능
        sessionStatus.setComplete();

        return "first/loginResult";
    }
}
