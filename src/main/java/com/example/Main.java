package com.example;
import java.util.Random;
import io.prometheus.client.Counter;
import io.prometheus.client.Summary;
import io.prometheus.client.exporter.HTTPServer;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class Main {    

    private static boolean pronto = false;

    private static final Counter ContadorOperacao = Counter.build()
    .name("total_operacoes_calculadora")
    .help("Total de operações realizadas na calculadora.")
    .labelNames("operacao")
    .register();

    private static final Summary DuracaoOperacao = Summary.build()
    .name("duracao_segundos_operacao_calculadora")
    .help("Duração das operações da calculadora em segundos.")
    .labelNames("operacao")
    .register();

public int Soma(int x, int y)
{
Summary.Timer tempo = DuracaoOperacao.labels("Soma").startTimer();
ContadorOperacao.labels("Soma").inc();
try
{
    return x + y;
}
finally
{
    tempo.observeDuration();
}

}

public int Divisao(int x, int y)
{   
Summary.Timer tempo = DuracaoOperacao.labels("Divisao").startTimer();
ContadorOperacao.labels("Divisao").inc();
try
{
    return x / y;
}
finally
{
    tempo.observeDuration();
}

}

public int Subtracao(int x, int y)
{
Summary.Timer tempo = DuracaoOperacao.labels("Subtracao").startTimer();
ContadorOperacao.labels("Subtracao").inc();
try
{
    return x - y;
}
finally
{
    tempo.observeDuration();
}

}

public int Multiplicacao(int x, int y)
{
Summary.Timer tempo = DuracaoOperacao.labels("Multiplicacao").startTimer();
ContadorOperacao.labels("Multiplicacao").inc();
try
{
    return x * y;
}
finally
{
    tempo.observeDuration();
}

}
    public static void main(String[] args) throws Exception {
        
        try
        {
       pronto = true;
        Main calculadora = new Main();
        HTTPServer servidor = new HTTPServer(15001);
        HttpServer server = HttpServer.create(new InetSocketAddress(15002), 0);
        server.createContext("/healthz", exchange -> {
            String response = "OK";
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        });
        server.createContext("/ready", exchange -> {
            String response = "READY";
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        });
        server.start();
        Random random = new Random();
while(true)
{
        System.out.println("Escolha qual operação quer usar:");
        System.out.println("1: Soma, 2: Subtracao, 3: Divisao, 4: Multiplicacao");
        int escolha = random.nextInt(1,4);
        System.out.println("Digite dois numeros:");
        int numero1 = random.nextInt(1,10);
        int numero2 = random.nextInt(1,10);
        
        switch (escolha)
        {
            case 1:
            System.out.println("O resultado é: " + calculadora.Soma(numero1, numero2));
            break;
            case 2:
            System.out.println("O resultado é: " + calculadora.Subtracao(numero1, numero2));
            break;
            case 3:
            System.out.println("O resultado é: " + calculadora.Divisao(numero1, numero2));
            break;
            case 4:
            System.out.println("O resultado é: " + calculadora.Multiplicacao(numero1, numero2));
            break;
        }
        
    }
    }
    catch(IOException e)
    {
        e.printStackTrace();
        System.exit(1);
    }
}
    
}