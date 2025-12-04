package Codes2024;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 {
    // 124055451 119285584 too high
    // 84827372 - first 4 lines
    // ..... - last 2 lines

    public static void main(String[] args) throws IOException {

        String substring;
        String regex = "mul\\(\\d{1,3},\\d{1,3}\\)";
        List<String> matchingInput = new ArrayList<>();
        long sumOfProducts = 0;

        //String line = "don't()xmul(2,4)&do()mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))";
        //String line = "?)do())mul(449,426)~mul(200/%#$$)select()+:who()mul(463,285)',{#+&mul(821,317{]what()mul(964,348)*when()what()>([[mul(291,602)what(590,172)$}++';/mul(831,385){mul(299,25)mul(402,592)):what()]mul(49,44)/ &?~<mul(652,698):select()<#/! mul*{;,mul(338,76)- '@?<}who(826,950)-mul(499,44)>~,@$$)^%mul(21,890)}when()]+mul(696,311)<&when()when()/who()%^&mul(967,563)*@why(38,563){%{(mul(539,838)''&%)!mul(140,574)@mul(872,800)$$*[};?(mul(589,800)'[[<))'what()mul(571,216)+{!$!mul(327,56)}>@mul(179]{mul(728,336))who()*}/#from(385,957)mul(528,971)how(607,239)who()when()>mul(699,576),[where()-?;,mul(953,122why()from())-mul(50,327))&]select()*mul(191$<*}+*;%?~mul(717,832);!,-;#mul(877,863)})mul(10,865)how()>~mul(471,191)when()+;-,:#+>{mul(98,339) ,%%who()select()mul(590from()^,from(943,773)/%/(mul(498,29)+[-when()mul(556,808)$,{why()'who()what()}select()$mul(408,17)}@^}%what()when(533,550)mul(119,401)<&?select()!!],mul(800,82)~'++'-^'mul(202,735)'-;how()(why()-mul(878,380)++:,'%>select()why(),mul(517,67):(,mul(57,468)]!!?^who()'who()how()(mul(362,246){[select()@-,mul(790,848)who()/how()&;~mul(896,459)mul(244,758)^do()]*mul(393,498)how(827,483)++[;$^mul(242,357)?mul(246,74)mul(243,685)$-*how(); @;<!mul(22,701)from()-;<mul(362,732)%do()<</[select(247,938)select())]mul(560,346)@(-:/from()mul(149,424) mul(662,460)from(736,545);where()how()[mul(56,361)${&#^][%:;mul(804,220)'why())&+: [why()&mul(64,840)mul(689,477)mul(279,571):^how(543,315)who()mul(552,111)/+who()mul(422,133)&&;from()what();mul(394,845+]mul(317,924)!'mul(300,16where()from()[mul(86,484)$where()?who()(&what()-#)don't()]?{:who(),)mul(710,37);#]what()mul(384,945)@]from()/(:mul(804,612))where()why()from()mul(247,945)-[)>${when()[mul(507,599)'(>mul(814,895)mul(334,391)):/don't()$~,'{)#&+@mul(346}~:{,from()/[mul(545,281))[!&**/,what()mul(505,164)mul(992,413)from()why()from()#}&#!%mul(570,812)/&:&%~*who()who()<mul(993,860)why()mul(5,416)mul(476,705)why()'~}}select()mul(399,720)from() ~(who(){why()&how()don't()@)+%how()&mul(842,172)'((&(#mul(305,49)how()what(){$}?   #mul(732,705)$}>,>%'#mul(849,141)mul(808,151)>'}~!>select()[:when()mul(547,749)/&+}how();when()$[:don't()from()&@&~mul(48,835)%:&){(who()<'mul(939,418):where()what()%mul(281,802)],who();^^#[mul(243,694)^[ mul(155,458)where(933,846)select()/)$>~%!$mul(412,437)mul(760,807)when()who()! what()<)who()mul(835,695)]&>when()#^mul(605,495) *)',:^*'mulwhat()what()}(,},/mul(268,328)]&mul(311,488)from(246,449):-!mul(728,913)who()]!why()*-why()({&mul(147,343)mul(87,236)from(233,81)#how()from()mul(72,161)!when()-/))mul(649,993)how()}where()where()~}do()<(mul(909,297)/?++mul(458,304)-mul(189,748):['!^%)mul(472,150)}#-mul(813,811)-<mul(235,856)'mul(207,698)mul(516,121)+mul(50,721), mul(102,815)[ select()+when()mul(25,317)mul(482,734)mul(205,924from()mul(866,589)how(242,498))*mul(427,953)select()what()'%?,},^mul(80,867)<mul(41,516),-#[,>who()mul(999,939)mul(748,494)why()<$$;!@{select()mul(679,545)who()!+%;mul(539,710)#}{#{do(){where()('(>*what(161,284)*]mul(822,730){)who()/mul(834,405)@-when()[why()^?#mul(651,356)don't()select()~ @+[<why()mul(511,258)%(;mul(799,627)-;^from()when()}mul(69,994)^;select()&~from()'mul(12,523)";
        List<String> lines = Files.readAllLines(Paths.get("2024/resources/Day3_input"));
        for(String line : lines){

            line = line.replace(" ", "");

//            while (line.contains(("don't()"))){
//                int indexOfDont = line.indexOf("don't()");
//                String substringAfterdont = line.substring(indexOfDont);
//                int indexOfDo = substringAfterdont.indexOf("do()");
//                indexOfDo = Math.addExact(indexOfDont , indexOfDo); // actual index in line
//                System.out.println("Index of dont = " + indexOfDont);
//                System.out.println("Index of do = " + indexOfDo);
//                if (indexOfDont < indexOfDo) {
//                    line = line.replace(line.substring(indexOfDont, indexOfDo+4), "");
//                    //System.out.println("Line after removal = " + line);
//                } else if (indexOfDo >= 0){
//                    line = line.replace(line.substring(indexOfDo, indexOfDo+4), "####");
//                }
//            }

            Pattern pattern = Pattern.compile("don't\\(\\).*?do\\(\\)");
            Matcher matcher = pattern.matcher(line);
            line = matcher.replaceAll("");

            System.out.println("Does the line still contain don't() ?? => " + line.contains("don't()"));
            while (line.contains("don't()")) {
                int indexOfDont = line.indexOf("don't()");
                line = line.substring(0, indexOfDont);
            }
            System.out.println("##### Final Line after removal = " + line);


            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c == 'm') {
                    for (int j = i+7; j<line.length(); j++) {
                        if (line.charAt(j) == ')' && Math.abs(i-j) < 12) {
                            String substringUnderProcess = line.substring(i, j + 1);
                            //System.out.println("Substring Under Process = " + substringUnderProcess);
                            substring = getExactSubstring(substringUnderProcess, regex, matchingInput);
                            //System.out.println("Exact Substring = " + substring);
                            if (!substring.equals("NotFound")){
                                sumOfProducts += calculateProduct(substring);
                            }
                            i = j;
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("############## Final Answer = " + sumOfProducts);

        long sum = 0;
        for(String s : matchingInput){
            sum += calculateProduct(s);
        }
        System.out.println("Based on list = " + sum);

    }

    private static long calculateProduct(String substring) {
        substring = substring.substring(4, substring.length() - 1);
        String[] numbers = substring.split(",");
        int product = Integer.parseInt(numbers[0]) * Integer.parseInt(numbers[1]);
        //System.out.println("Product = " + product);
        return product;
    }

    private static String getExactSubstring(String str, String regex, List<String> matchingInput) {
        if (str.matches(regex)) {
            matchingInput.add(str);
            System.out.println(str);
            return str;
        } else {
            for (int i = str.length() - 1; i >= 7; i--) {
                String temp;
                if (str.charAt(i) == ')') {
                    temp = str.substring(0, i + 1);
                    System.out.println("*** = "+temp);
                    if (temp.matches(regex)) {
                        matchingInput.add(temp);
                        System.out.println(temp);
                        return temp;
                    } else {
                        System.out.println("Temp does not match regex = " + temp);
                    }
                }
            }
        }
        return "NotFound";
    }

}
