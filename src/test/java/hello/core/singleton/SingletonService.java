package hello.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();
    //자기 자신을 static으로 만들어둠

    public static SingletonService getInstance() {
        return instance;
    }

    private SingletonService() {}

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }
}
