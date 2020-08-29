package Algorithm;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.ArrayList;

public class Testing {
    public static void main(String[] args){
        ArrayList<Node> testarr = new ArrayList<Node>();

        Node n1 = new Node("1");
        Node n2 = new Node("2");

        testarr.add(n1);
        testarr.add(n2);

        if(testarr.containsAll(new ArrayList<Node>())){
            System.out.println("Hi");
        }else{
            System.out.println("Byee");
        }
    }
}
