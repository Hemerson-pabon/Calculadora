package org.calculadora;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class AnalizadorExpresiones {
    private static final Map<String, BiFunction<BigDecimal, BigDecimal, BigDecimal>> OPERADORES = new HashMap<>();
    private static final Map<String, Function<BigDecimal, BigDecimal>> FUNCIONES = new HashMap<>();
    private static final Map<String, BigDecimal> CONSTANTES = new HashMap<>();
    private static final Funciones FP = new Funciones();
    private static final int PRECISION = 100; // Valor de n para las series de potencias
    private static final MathContext MC = new MathContext(PRECISION, RoundingMode.HALF_UP);

    public AnalizadorExpresiones(){
        FP.setMc(MC);
    }

    static {
        // Definir operadores básicos
        OPERADORES.put("+", (a, b) -> a.add(b, MC));
        OPERADORES.put("-", (a, b) -> a.subtract(b, MC));
        OPERADORES.put("*", (a, b) -> a.multiply(b, MC));
        OPERADORES.put("/", (a, b) -> a.divide(b, MC));

        // Definir funciones matemáticas
        FUNCIONES.put("sin", (x) -> FP.sin(x, PRECISION));
        FUNCIONES.put("cos", (x) -> FP.cos(x, PRECISION));
        FUNCIONES.put("sqrt", FP::sqrt);
        FUNCIONES.put("exp", (x) -> FP.e(x, PRECISION));
        FUNCIONES.put("ln", (x) -> FP.ln(x, PRECISION));

        // Definir constantes matemáticas
        CONSTANTES.put("pi", new BigDecimal("3.141592653589793238462643383279502884197169399375105820974944592307816406286208998628034825342117067982148086513282306647093844609550582231725359408128481117450284102701938521105559644622948954930381964428810975665933446128475648233786783165271201909145648566923460348610454326648213393607260249141273724587006606315588174881520920962829254091715364367892590360011330530548820466521384146951941511609433057270365759591953092186117381932611793105118548074462379962749567351885752724891227938183011949129833673362440656643086021394946395224737190702179860943702770539217176293176752384674818467669405132000568127145263560827785771342757789609173637178721468440901224953430146549585371050792279689258923542019956112129021960864034418159813629774771309960518707211349999998372978049951059731732816096318595024459455346908302642522308253344685035261931188171010003137838752886587533208381420617177669147303598253490428755468731159562863882353787593751957781857780532171226806613001927876611195909216420198"));
        // CONSTANTES.put("e", new BigDecimal("2.718281828459045235360287471352662497757247093699959574966967627724076630353547594571382178525166427427466391932003059921817413596629043572900334295260595630738132328627943490763233829880753195251019011573834187930702154089149934884167509244761460668082264800168477411853742345442437107539077744992069551702761838606261331384583000752044933826560297606737113200709328709127443747047230696977209310141692836819025515108657463772111252389784425056953696770785449969967946864454905987931636889230098793127736178215424999229576351482208269895193668033182528869398496465105820939239829488793320362509443117301238197068416140397019837679320683282376464804295311802328782509819455815301756717361332069811250996181881593041690351598888519345807273866738589422879228499892086805825749279610484198444363463244968487560233624827041978623209002160990235304369941849146314093431738143640546253152096183690888707016768396424378140592714563549061303107208510383750510115747704171898610687396965521267154688957035035402123407849819334321068170121005627880235193033224745015853904730419957777093503660416997329725088687696640355570716226844716256079882651787134195124665201030592123667719432527867539855894489697096409754591856956380236370162112047742722836489613422516445078182442352948636372141740238893441247963574370263755294448337998016125492278509257782562092622648326277933386566481627725164019105900491644998289315056604725802778631864155195653244258698294695930801915298721172556347546396447910145904090586298496791287406870504895858671747985466775757320568128845920541334053922000113786300945560688166740016984205580403363795376452030402432256613527836951177883863874439662532249850654995886234281899707733276171783928034946501434558897071942586398772754710962953741521115136835062752602326484728703920764310059584116612054529703023647254929666938115137322753645098889031360205724817658511806303644281231496550704751025446501172721155519486685080036853228183152196003735625279449515828418829478761085263981395599006737648292244375287184624578036192981971399147564488262603903381441823262515097482798777996437308997038886778227138360577297882412561190717663946507063304527954661855096666185664709711344474016070462621568071748187784437143698821855967095910259686200235371858874856965220005031173439207321139080329363447972735595527734907178379342163701205005451326383544000186323991490705479778056697853358048966906295119432473099587655236812859041383241160722602998330535370876138939245508308751045936012794156777193"),
        // CONSTANTES.put("phi", new BigDecimal("1.618033988749894848204586834365638117720309179805762862135448622705260462818902449707207204189391137484754088075386891752126633862223536931793180060766726354433389086595939582905638322661319928290267880675208766892501711696207032221043216269548626296313614438149758701220340805887954454749246185695364864449241044320771344947049565846788509874339442212544877066478091588460749988712400765217057517978834166256249407589069704000281210427621771117778053153171410117046665991466979873176135600670874807101317952368942752194843530567830022878569978297783478458782289110976250030269615617002504643382437764861028383126833037242926752631165339247316711121158818638513316203840052221657912866752946549068113171599343235973494985090409476213222981017261070596116456299098162905552085247903524060201727997471753427775927786256194320827505131218156285512224809394712341451702237358057727861600868838295230459264787801788992199027077690389532196819861514378031499741106926088674296226757560523172777520353613936210767389376455606060592165894667595519004005559089502295309425);"));

    }

    public static BigDecimal evaluarExpresion(String expresion, Map<String, BigDecimal> variables) {
        expresion = expresion.replaceAll("\\s+", "").toLowerCase();

        try {
            return evaluarRecursivo(expresion, variables);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al evaluar la expresión: " + e.getMessage());
        }
    }

    private static BigDecimal evaluarRecursivo(String expresion, Map<String, BigDecimal> variables) {
        expresion = eliminarParentesisExternos(expresion);

        // Evaluar funciones matemáticas primero
        for (String nombreFuncion : FUNCIONES.keySet()) {
            if (expresion.startsWith(nombreFuncion + "(")) {
                int finParentesis = encontrarParentesisCierre(expresion, nombreFuncion.length());
                String argumentoStr = expresion.substring(nombreFuncion.length() + 1, finParentesis);
                BigDecimal argumento = evaluarRecursivo(argumentoStr, variables);
                String restoExpresion = finParentesis < expresion.length() - 1 ?
                        expresion.substring(finParentesis + 1) : "";

                if (restoExpresion.isEmpty()) {
                    return FUNCIONES.get(nombreFuncion).apply(argumento);
                } else {
                    expresion = FUNCIONES.get(nombreFuncion).apply(argumento).toString() + restoExpresion;
                }
            }
        }

        // Buscar operadores de menor precedencia primero (suma y resta)
        for (int i = expresion.length() - 1; i >= 0; i--) {
            char c = expresion.charAt(i);
            if (c == '+' || c == '-') {
                if (i == 0 || expresion.charAt(i - 1) == '(' || expresion.charAt(i - 1) == '^' ||
                        expresion.charAt(i - 1) == '*' || expresion.charAt(i - 1) == '/') {
                    continue; // Es un signo unario
                }
                String izquierda = expresion.substring(0, i);
                String derecha = expresion.substring(i + 1);
                BiFunction<BigDecimal, BigDecimal, BigDecimal> operador = OPERADORES.get(String.valueOf(c));
                return operador.apply(
                        evaluarRecursivo(izquierda, variables),
                        evaluarRecursivo(derecha, variables)
                );
            }
        }

        // Buscar operadores de multiplicación y división
        for (int i = expresion.length() - 1; i >= 0; i--) {
            char c = expresion.charAt(i);
            if (c == '*' || c == '/') {
                String izquierda = expresion.substring(0, i);
                String derecha = expresion.substring(i + 1);
                BiFunction<BigDecimal, BigDecimal, BigDecimal> operador = OPERADORES.get(String.valueOf(c));
                return operador.apply(
                        evaluarRecursivo(izquierda, variables),
                        evaluarRecursivo(derecha, variables)
                );
            }
        }

        // Buscar operador de potencia
        for (int i = 0; i < expresion.length(); i++) {
            if (expresion.charAt(i) == '^') {
                String base = expresion.substring(0, i);
                String exponente = expresion.substring(i + 1);
                return FP.a_elevado(
                        evaluarRecursivo(base, variables),
                        evaluarRecursivo(exponente, variables),
                        PRECISION
                );
            }
        }

        // Evaluar números, constantes y variables
        if (expresion.matches("-?\\d+(\\.\\d+)?")) {
            return new BigDecimal(expresion);
        } else if (CONSTANTES.containsKey(expresion)) {
            return CONSTANTES.get(expresion);
        } else if (variables.containsKey(expresion)) {
            return variables.get(expresion);
        } else {
            throw new IllegalArgumentException("Constante, variable no definida o expresión inválida: " + expresion);
        }
    }

    private static String eliminarParentesisExternos(String expresion) {
        while (expresion.startsWith("(") && expresion.endsWith(")")) {
            int finParentesis = encontrarParentesisCierre(expresion, 0);
            if (finParentesis == expresion.length() - 1) {
                expresion = expresion.substring(1, expresion.length() - 1);
            } else {
                break;
            }
        }
        return expresion;
    }

    private static int encontrarParentesisCierre(String expresion, int inicio) {
        int contador = 0;
        for (int i = inicio; i < expresion.length(); i++) {
            if (expresion.charAt(i) == '(') {
                contador++;
            }
            if (expresion.charAt(i) == ')') {
                contador--;
                if (contador == 0) {
                    return i;
                }
            }
        }
        throw new IllegalArgumentException("Paréntesis no balanceados");
    }
}