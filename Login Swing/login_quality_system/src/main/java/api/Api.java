package api;

import com.github.britooo.looca.api.core.Looca;

public class Api {
    public static void main(String[] args) {
        Looca looca = new Looca();
        
        
        System.out.println(looca.getProcessador().getFabricante());
    }
}
