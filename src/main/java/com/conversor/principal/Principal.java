package com.conversor.principal;

import com.conversor.modelos.Moneda;
import com.conversor.servicios.ConsultaMoneda;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        Scanner lectura = new Scanner(System.in);
        ConsultaMoneda consulta = new ConsultaMoneda();
        int opcion = 0;

        String menu = """
                *************************************************
                Sea bienvenido/a al Conversor de Monedas
                
                1) Dólar => Peso Argentino
                2) Peso Argentino => Dólar
                3) Dólar => Real Brasileño
                4) Real Brasileño => Dólar
                5) Dólar => Peso Colombiano
                6) Peso Colombiano => Dólar
                7) Salir
                
                Elija una opción válida:
                *************************************************""";

        while (opcion != 7) {
            System.out.println(menu);
            try {
                opcion = Integer.parseInt(lectura.nextLine());

                switch (opcion) {
                    case 1:
                        convertir("USD", "ARS", consulta, lectura);
                        break;
                    case 2:
                        convertir("ARS", "USD", consulta, lectura);
                        break;
                    case 3:
                        convertir("USD", "BRL", consulta, lectura);
                        break;
                    case 4:
                        convertir("BRL", "USD", consulta, lectura);
                        break;
                    case 5:
                        convertir("USD", "COP", consulta, lectura);
                        break;
                    case 6:
                        convertir("COP", "USD", consulta, lectura);
                        break;
                    case 7:
                        System.out.println("Cerrando la aplicación... ¡Gracias por utilizar el servicio!");
                        break;
                    default:
                        System.out.println("Opción no válida. Por favor intente de nuevo.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            }
        }
    }

    private static void convertir(String monedaBase, String monedaObjetivo, ConsultaMoneda consulta, Scanner lectura) {
        System.out.println("Ingrese el valor que deseas convertir: ");
        double cantidad;
        try {
            cantidad = Double.parseDouble(lectura.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Inténtelo de nuevo.");
            return;
        }

        System.out.println("Consultando tasas de cambio...");
        try {
            Moneda monedaBaseResponse = consulta.buscarMoneda(monedaBase);
            
            if (monedaBaseResponse.conversion_rates() != null && monedaBaseResponse.conversion_rates().containsKey(monedaObjetivo)) {
                double tasa = monedaBaseResponse.conversion_rates().get(monedaObjetivo);
                double resultado = cantidad * tasa;
                System.out.printf("El valor de %.2f [%s] corresponde al valor final de = %.2f [%s]\n", 
                                cantidad, monedaBase, resultado, monedaObjetivo);
            } else {
                System.out.println("No se pudo obtener la tasa de cambio para " + monedaObjetivo);
            }
        } catch (RuntimeException e) {
            System.out.println("Error de red o API: " + e.getMessage());
        }
        System.out.println("");
    }
}
