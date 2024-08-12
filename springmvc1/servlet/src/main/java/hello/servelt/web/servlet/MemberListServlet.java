package hello.servelt.web.servlet;

import hello.servelt.domain.member.Member;
import hello.servelt.domain.member.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name ="MemberListServlet", urlPatterns = "/servlet/members")
public class MemberListServlet extends HttpServlet {

    private MemberRepository memberRepository = MemberRepository.getInstance();


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Member> members = memberRepository.findAll();

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.write("<html>");
        out.write("<head>");
        out.write(" <meta charset=\"UTF-8\">");
        out.write(" <title>Title</title>");
        out.write("</head>");
        out.write("<body>");
        out.write("<a href=\"/index.html\">메인</a>");
        out.write("<table>");
        out.write(" <thead>");
        out.write(" <th>id</th>");
        out.write(" <th>username</th>");
        out.write(" <th>age</th>");
        out.write(" </thead>");
        out.write(" <tbody>");
        /*
        out.write(" <tr>");
         out.write(" <td>1</td>");
         out.write(" <td>userA</td>"); out.write(" <td>10</td>");
         out.write(" </tr>");
        */
        for (Member member : members) {
            out.write(" <tr>");
            out.write(" <td>" + member.getId() + "</td>");
            out.write(" <td>" + member.getUsername() + "</td>");
            out.write(" <td>" + member.getAge() + "</td>");
            out.write(" </tr>");
        }
        out.write(" </tbody>");
        out.write("</table>");
        out.write("</body>");
        out.write("</html>");
    }
}
