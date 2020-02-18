package com.kajalbordhon.persistemce;

import com.kajalbordhon.data.AttachmentBean;
import com.kajalbordhon.data.EmailBean;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Insert 25 emails to the database. For the DAO test class.
 *
 * @author kajal
 */
public class EmailBeanFactory {

    /**
     *  Logger
     */
    private final static Logger LOG = LoggerFactory.getLogger(FolderDAO.class);

    /**
     *  EmailDAO
     */
    private final EmailDAO dao = new EmailDAO();

    /**
     *  Constructor
     */
    public EmailBeanFactory() {
    }

    /**
     * Creates 25 emails.
     *
     * @throws SQLException
     * @throws IOException
     */
    public void summon() throws SQLException, IOException {
        LOG.info("Summoning 25 emails");
        AttachmentBean picAttach = null;
        AttachmentBean picAttach2 = null;
        AttachmentBean picAttach3 = null;
        AttachmentBean picEmb = null;
        try {

            //attachment
            File file = new File("coffee.jpg");
            byte[] fileContent = Files.readAllBytes(file.toPath());
            picAttach = new AttachmentBean(fileContent, file.getName(), false);

            //attachment
            file = new File("cat.jpg");
            byte[] fileContent2 = Files.readAllBytes(file.toPath());
            picAttach2 = new AttachmentBean(fileContent2, file.getName(),false);

            //attachment
            file = new File("fine.jpg");
            byte[] fileContent4 = Files.readAllBytes(file.toPath());
            picAttach3 = new AttachmentBean(fileContent4, file.getName(), false);

            //embedded
            file = new File("why.jpg");
            byte[] fileContent3 = Files.readAllBytes(file.toPath());
            picEmb = new AttachmentBean(fileContent3, file.getName(), true);
        } catch (IOException ex) {
            LOG.debug("summon", ex.getMessage());
            throw ex;
        }

        AttachmentBean[] a1 = new AttachmentBean[]{picAttach};
        AttachmentBean[] a2 = new AttachmentBean[]{picEmb};
        AttachmentBean[] a3 = new AttachmentBean[]{picAttach3};
        AttachmentBean[] a4 = new AttachmentBean[]{picAttach2};
        AttachmentBean[] a5 = new AttachmentBean[]{picAttach, picEmb};
        AttachmentBean[] a6 = new AttachmentBean[]{picAttach, picEmb, picAttach3};
        AttachmentBean[] a7 = new AttachmentBean[]{picAttach, picEmb, picAttach3, picAttach2};

        this.dao.pushEmail(
                new EmailBean("TonyCMorales@armyspy.com",
                        new String[]{"RodneyLNelson@jourrapide.com"},
                        null,
                        null,
                        "himenaeos id litora enim aenean",
                        "Arcu vulputate class urna risus etiam est dictum sit mi ultrices, vulputate ultrices enim primis elementum phasellus praesent suscipit at, nisl quisque etiam nulla ornare aliquam lectus duis curabitur vehicula ac curae lobortis mattis iaculis elementum vitae per neque, vitae lorem elementum ultricies congue nostra pellentesque ornare, vivamus tristique curabitur lobortis libero pulvinar pretium id etiam ad in tortor vehicula interdum consequat nec egestas, dapibus urna vivamus urna fames rutrum venenatis, primis faucibus aenean platea ut felis velit interdum ipsum nam gravida non iaculis condimentum aenean lacinia maecenas.",
                        "<p>Lorem ipsum dapibus lorem at praesent libero nisi porttitor hendrerit, ut neque gravida leo curabitur maecenas magna ornare, quis vitae amet sit ligula eu vestibulum nisl rhoncus suspendisse orci integer nam tincidunt donec vehicula.</p>",
                        a1,
                        "Inbox",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("BeatriceJVogt@dayrep.com",
                        new String[]{"TonyCMorales@armyspy.com", "BeatriceJVogt@dayrep.com", "TracyLYancy@armyspy.com", "MichaelSPrice@jourrapide.com", "AntionetteJCarranza@jourrapide.com"},
                        new String[]{"RobinKSchulze@dayrep.com", "BarbaraARogers@armyspy.com", "FredMParmenter@rhyta.com"},
                        new String[]{"AugustineSBoaz@jourrapide.com", "AnnaCSainz@teleworm.us"},
                        "Lorem ipsum maecenas",
                        "Lorem ipsum dapibus lorem at praesent libero nisi porttitor hendrerit, ut neque gravida leo curabitur maecenas magna ornare, quis vitae amet sit ligula eu vestibulum nisl rhoncus suspendisse orci integer nam tincidunt donec vehicula.",
                        "<p>Cursus ultricies fringilla metus nullam himenaeos id litora enim aenean, neque mattis risus ligula ac blandit eros tincidunt arcu sed, iaculis dictum nam mauris primis class tristique convallis.</p>",
                        a1,
                        "Inbox",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("TracyLYancy@armyspy.com",
                        new String[]{"TracyLYancy@armyspy.com", "MichaelSPrice@jourrapide.com"},
                        new String[]{"FloydADixon@rhyta.com", "RandyLGiles@armyspy.com", "DebraWMartinez@armyspy.com", "WillieBGoris@jourrapide.com", "GaryEDimattia@armyspy.com"},
                        new String[]{"RobinKSchulze@dayrep.com", "BarbaraARogers@armyspy.com", "FredMParmenter@rhyta.com", "JacquelineAButler@jourrapide.com", "TimothyCGuy@dayrep.com"},
                        "velit lectus mauris dictumst id interdum risus", "Lorem ipsum dapibus lorem at praesent libero nisi porttitor hendrerit, ut neque gravida leo curabitur maecenas magna ornare, quis vitae amet sit ligula eu vestibulum nisl rhoncus suspendisse orci integer nam tincidunt donec vehicula.",
                        "<p>Ornare posuere ullamcorper posuere mauris placerat vivamus duis curae, tristique quisque suscipit condimentum euismod posuere enim, sociosqu proin pulvinar auctor dapibus metus convallis diam nunc libero proin nunc etiam pellentesque aliquam arcu metus eu magna, cras fermentum condimentum aliquam porta sapien eros torquent augue tempus, sociosqu class felis curae consectetur est nam aliquet nam est sodales elementum nisl feugiat orci hendrerit ac vehicula turpis facilisis congue himenaeos nibh class risus, congue etiam purus rutrum ligula venenatis aliquet turpis commodo proin magna vestibulum.</p>",
                        a5,
                        "Sent",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("MichaelSPrice@jourrapide.com",
                        new String[]{"FredMParmenter@rhyta.com", "JacquelineAButler@jourrapide.com"},
                        new String[]{"MarkJSmith@jourrapide.com", "RodneyLNelson@jourrapide.com", "JennaLRivera@rhyta.com", "TravisOLewis@armyspy.com", "RayDStewart@dayrep.com"},
                        new String[]{"AugustineSBoaz@jourrapide.com", "AnnaCSainz@teleworm.us"},
                        "auctor lectus imperdiet ",
                        "Blandit tellus viverra fames rutrum nibh porttitor sagittis cursus velit torquent fames ut elit at nostra, blandit ultricies venenatis libero pellentesque nulla interdum sodales hac himenaeos sem phasellus senectus turpis nam consequat scelerisque elementum vel rhoncus est nam, curae ac pulvinar aenean amet ante risus aliquam faucibus felis nisl magna euismod praesent quis massa porttitor erat primis, nibh purus dictumst elit ante pharetra vel dui mollis netus integer pulvinar amet himenaeos id nunc diam netus in.",
                        "<p>Commodo orci rutrum nam suscipit vehicula in lorem inceptos conubia, vestibulum sollicitudin commodo ante senectus et lorem.</p>",
                        a2,
                        "Important",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("AntionetteJCarranza@jourrapide.com",
                        new String[]{"VonnieLGrier@armyspy.com", "RayPGregory@dayrep.com", "AugustineSBoaz@jourrapide.com", "AnnaCSainz@teleworm.us"},
                        new String[]{"DaleGReynolds@armyspy.com", "VonnieLGrier@armyspy.com", "RayPGregory@dayrep.com", "AugustineSBoaz@jourrapide.com", "AnnaCSainz@teleworm.us"},
                        new String[]{"MarkJSmith@jourrapide.com", "RodneyLNelson@jourrapide.com"},
                        "vehicula rhoncus mauris, phasellus litora", "Cursus ultricies fringilla metus nullam himenaeos id litora enim aenean, neque mattis risus ligula ac blandit eros tincidunt arcu sed, iaculis dictum nam mauris primis class tristique convallis.",
                        "<p>Blandit tellus viverra fames rutrum nibh porttitor sagittis cursus velit torquent fames ut elit at nostra, blandit ultricies venenatis libero pellentesque nulla interdum sodales hac himenaeos sem phasellus senectus turpis nam consequat scelerisque elementum vel rhoncus est nam, curae ac pulvinar aenean amet ante risus aliquam faucibus felis nisl magna euismod praesent quis massa porttitor erat primis, nibh purus dictumst elit ante pharetra vel dui mollis netus integer pulvinar amet himenaeos id nunc diam netus in.</p>",
                        a6,
                        "Sent",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("RobinKSchulze@dayrep.com",
                        new String[]{"VonnieLGrier@armyspy.com", "RayPGregory@dayrep.com", "AugustineSBoaz@jourrapide.com"},
                        new String[]{"RandyLGiles@armyspy.com", "DebraWMartinez@armyspy.com", "WillieBGoris@jourrapide.com"},
                        new String[]{"AugustineSBoaz@jourrapide.com", "AnnaCSainz@teleworm.us"},
                        "facilisis donec tristique", "ante litora, nisi auctor amet fermentum fringilla aliquet mattis sem aptent vestibulum eros lobortis turpis auctor pharetra et, pretium cursus metus inceptos auctor aenean platea nisl ligula varius ut ullamcorper fermentum vitae egestas nam, dictum ante malesuada habitasse arcu turpis ",
                        "<h1>Mattis praesent suspendisse etiam odio adipiscing metus non placerat, etiam ac nostra tortor cursus euismod iaculis aliquam, tellus maecenas purus lacinia integer sit curabitur taciti interdum convallis vehicula platea posuere hendrerit et cras gravida lectus rhoncus, pretium litora feugiat conubia nisi at platea viverra donec</h1>",
                        a3,
                        "Sent",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("BarbaraARogers@armyspy.com",
                        new String[]{"FredMParmenter@rhyta.com", "JacquelineAButler@jourrapide.com"},
                        new String[]{"AugustineSBoaz@jourrapide.com", "AnnaCSainz@teleworm.us"},
                        new String[]{"DaleGReynolds@armyspy.com", "VonnieLGrier@armyspy.com"},
                        "euismod vestibulum cras aptent", "elementum phasellus praesent suscipit at, nisl quisque etiam nulla ornare aliquam lectus duis curabitur vehicula ac curae lobortis mattis iaculis elementum vitae per neque, vitae lorem elementum ultricies congue nostra pellentesque ornare, vivamus tristique curabitur lobortis libero pulvinar pretium id etiam ad in",
                        "<h1>Arcu</h1>",
                        a4,
                        "Important",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("FredMParmenter@rhyta.com",
                        new String[]{"MarkJSmith@jourrapide.com", "RodneyLNelson@jourrapide.com", "JennaLRivera@rhyta.com"},
                        new String[]{"VonnieLGrier@armyspy.com", "RayPGregory@dayrep.com", "AugustineSBoaz@jourrapide.com", "AnnaCSainz@teleworm.us"},
                        new String[]{"RobinKSchulze@dayrep.com", "BarbaraARogers@armyspy.com", "FredMParmenter@rhyta.com", "JacquelineAButler@jourrapide.com", "TimothyCGuy@dayrep.com"},
                        "nullam rutrum dictumst ",
                        "Velit himenaeos euismod etiam laoreet senectus dapibus lorem ad curabitur leo, porttitor sed ante vel conubia nisi est at vitae faucibus, nostra aliquet odio mattis suspendisse neque libero tellus ligula lectus est lorem semper rutrum diam hac imperdiet, hac facilisis curae suscipit molestie bibendum, tempus laoreet fermentum rutrum scelerisque morbi.",
                        "<h1>Dolor ultrices sagittis adipiscing netus vel aptent nisl bibendum, in risus urna laoreet inceptos mi nec, tincidunt imperdiet suscipit sed praesent lacinia aliquam vitae, fringilla massa vehicula commodo dictumst hendrerit fames pulvinar aenean fames pretium donec sit tincidunt orci id, fusce lacinia congue sodales a praesent sociosqu ac, eu semper fusce rutrum tempor placerat phasellus tempor</h1>",
                        a1,
                        "Work",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("JacquelineAButler@jourrapide.com",
                        new String[]{"MarkJSmith@jourrapide.com", "RodneyLNelson@jourrapide.com", "JennaLRivera@rhyta.com"},
                        new String[]{"RandyLGiles@armyspy.com", "DebraWMartinez@armyspy.com", "WillieBGoris@jourrapide.com"},
                        new String[]{"JennaLRivera@rhyta.com", "TravisOLewis@armyspy.com", "RayDStewart@dayrep.com"},
                        "phasellus sollicitudin vitae",
                        "Velit himenaeos euismod etiam laoreet senectus dapibus lorem ad curabitur leo, porttitor sed ante vel conubia nisi est at vitae faucibus, nostra aliquet odio mattis suspendisse neque libero tellus ligula lectus est lorem semper rutrum diam hac imperdiet, hac facilisis curae suscipit molestie bibendum, tempus laoreet fermentum",
                        "<p>Etiam bibendum aenean massa neque sed ad vitae ut, congue vivamus senectus egestas proin nisi ante litora, nisi auctor amet fermentum fringilla aliquet mattis sem aptent vestibulum eros lobortis turpis auctor pharetra et, pretium cursus metus inceptos auctor aenean platea nisl ligula varius ut ullamcorper fermentum vitae egestas nam, dictum ante malesuada habitasse arcu turpis semper nunc nulla, a id felis duis per ut ultrices suscipit.</p>",
                        a5,
                        "Inbox",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("TimothyCGuy@dayrep.com",
                        new String[]{"FloydADixon@rhyta.com", "RandyLGiles@armyspy.com", "DebraWMartinez@armyspy.com", "WillieBGoris@jourrapide.com", "GaryEDimattia@armyspy.com"},
                        new String[]{"BeatriceJVogt@dayrep.com", "TracyLYancy@armyspy.com", "MichaelSPrice@jourrapide.com", "AntionetteJCarranza@jourrapide.com"},
                        new String[]{"JennaLRivera@rhyta.com", "TravisOLewis@armyspy.com", "RayDStewart@dayrep.com"},
                        "fames sit etiam aliquet pretium",
                        "Ipsum volutpat phasellus vel cursus aliquam volutpat augue urna aenean, libero potenti id donec eu pulvinar curae viverra quisque, aliquet lectus bibendum taciti at posuere tristique molestie blandit fringilla habitant ornare aliquam maecenas etiam lorem, elit tempus nam vivamus fusce malesuada, placerat malesuada morbi platea lorem lacus curabitur facilisis etiam vulputate augue non ante ultricies senectus ullamcorper, congue per vivamus turpis aenean libero netus dictumst vel libero, lorem vivamus tellus posuere tellus ligula nibh dapibus.",
                        "<p>Phasellus blandit.</p>",
                        null,
                        "Inbox",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("MarkJSmith@jourrapide.com",
                        new String[]{"VonnieLGrier@armyspy.com", "RayPGregory@dayrep.com", "AugustineSBoaz@jourrapide.com", "AnnaCSainz@teleworm.us"},
                        new String[]{"AugustineSBoaz@jourrapide.com", "AnnaCSainz@teleworm.us"},
                        new String[]{"MarkJSmith@jourrapide.com", "RodneyLNelson@jourrapide.com", "JennaLRivera@rhyta.com", "TravisOLewis@armyspy.com", "RayDStewart@dayrep.com"},
                        "Convallis lacus facilisis sem dapibus",
                        "Phasellus dapibus curabitur varius eleifend nisl enim quis arcu dui fames, habitasse nisl ut sollicitudin elementum tempor amet rutrum mollis, ultricies eleifend porttitor sagittis luctus lorem rutrum euismod curabitur sapien himenaeos curae morbi nunc scelerisque porta nisl ante dictumst viverra nostra fusce, sed habitant a cursus tristique nam aliquam magna urna integer euismod ullamcorper sociosqu vitae sed class porttitor convallis class condimentum porttitor dapibus etiam lectus, venenatis lacus tellus sodales dictum vehicula sollicitudin lacinia platea lacinia elementum duis ut amet per.",
                        "<p>Arcu vulputate class urna risus etiam est dictum sit mi ultrices, vulputate ultrices enim primis elementum phasellus praesent suscipit at, nisl quisque etiam nulla ornare aliquam lectus duis curabitur vehicula ac curae lobortis mattis iaculis elementum vitae per neque, vitae lorem elementum ultricies congue nostra pellentesque ornare, vivamus tristique curabitur lobortis libero pulvinar pretium id etiam ad in tortor vehicula interdum consequat nec egestas, dapibus urna vivamus urna fames rutrum venenatis, primis faucibus aenean platea ut felis velit interdum ipsum nam gravida non iaculis condimentum aenean lacinia maecenas.</p>",
                        a1,
                        "Work",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("RodneyLNelson@jourrapide.com",
                        new String[]{"RandyLGiles@armyspy.com", "DebraWMartinez@armyspy.com", "WillieBGoris@jourrapide.com"},
                        new String[]{"TonyCMorales@armyspy.com", "BeatriceJVogt@dayrep.com", "TracyLYancy@armyspy.com", "MichaelSPrice@jourrapide.com", "AntionetteJCarranza@jourrapide.com"},
                        new String[]{"MarkJSmith@jourrapide.com", "RodneyLNelson@jourrapide.com"},
                        "blandit ad sollicitudin iaculis, luctus",
                        "Phasellus blandit.",
                        "<p>Velit himenaeos euismod etiam laoreet senectus dapibus lorem ad curabitur leo, porttitor sed ante vel conubia nisi est at vitae faucibus, nostra aliquet odio mattis suspendisse neque libero tellus ligula lectus est lorem semper rutrum diam hac imperdiet, hac facilisis curae suscipit molestie bibendum, tempus laoreet fermentum rutrum scelerisque morbi.</p>",
                        a7,
                        "Inbox",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("JennaLRivera@rhyta.com",
                        new String[]{"RodneyLNelson@jourrapide.com", "JennaLRivera@rhyta.com", "TravisOLewis@armyspy.com"},
                        new String[]{"DaleGReynolds@armyspy.com", "VonnieLGrier@armyspy.com"},
                        new String[]{"MarkJSmith@jourrapide.com", "RodneyLNelson@jourrapide.com"},
                        "mollis ante donec tincidunt",
                        "Proin metus tellus dolor eleifend interdum ac sagittis inceptos, molestie venenatis mattis himenaeos nibh tellus facilisis consectetur neque, nullam et eleifend ut pretium sem inceptos conubia aliquam proin mollis varius pulvinar praesent, auctor faucibus vulputate sodales aptent pharetra nullam, ullamcorper nunc sagittis massa nunc tincidunt faucibus aenean habitant.",
                        "<h1>Litora torquent risus vivamus dapibus consequat nec vehicula ipsum, viverra neque a aptent arcu potenti nulla, ultrices fusce lacinia hac nostra facilisis euismod</h1>",
                        a1,
                        "School",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("TravisOLewis@armyspy.com",
                        new String[]{"FloydADixon@rhyta.com", "RandyLGiles@armyspy.com", "DebraWMartinez@armyspy.com", "WillieBGoris@jourrapide.com", "GaryEDimattia@armyspy.com"},
                        new String[]{"JennaLRivera@rhyta.com", "TravisOLewis@armyspy.com", "RayDStewart@dayrep.com"},
                        new String[]{"MarkJSmith@jourrapide.com", "RodneyLNelson@jourrapide.com"},
                        "rutrum cras volutpat ",
                        "Ante varius odio enim consequat enim suspendisse euismod netus ultrices vehicula, augue amet augue ante lectus consectetur primis purus id cubilia, facilisis ut eros ac vestibulum imperdiet curabitur quisque non diam etiam ultricies eget semper volutpat eget cubilia class id tempus enim risus consectetur praesent, egestas fusce sodales fermentum habitasse lacus in vehicula quisque odio torquent bibendum mattis facilisis rutrum conubia eget amet viverra gravida nisl suscipit, diam rutrum habitant augue quisque tristique tellus, aliquam et eget aptent curae a porta.",
                        "<p>Ante varius odio enim consequat enim suspendisse euismod netus ultrices vehicula, augue amet augue ante lectus consectetur primis purus id cubilia, facilisis ut eros ac vestibulum imperdiet curabitur quisque non diam etiam ultricies eget semper volutpat eget cubilia class id tempus enim risus consectetur praesent, egestas fusce sodales fermentum habitasse lacus in vehicula quisque odio torquent bibendum mattis facilisis rutrum conubia eget amet viverra gravida nisl suscipit, diam rutrum habitant augue quisque tristique tellus, aliquam et eget aptent curae a porta.</p>",
                        a6,
                        "Inbox",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("RayDStewart@dayrep.com",
                        new String[]{"RodneyLNelson@jourrapide.com", "JennaLRivera@rhyta.com", "TravisOLewis@armyspy.com"},
                        new String[]{"DaleGReynolds@armyspy.com", "VonnieLGrier@armyspy.com"},
                        new String[]{"AugustineSBoaz@jourrapide.com", "AnnaCSainz@teleworm.us"},
                        "Per duis proin consectetur faucibus", "Id posuere id sit ultrices nisi porttitor, lacinia accumsan quam eros fusce, ullamcorper litora consectetur vitae sed venenatis placerat accumsan donec quam quis ut semper aenean ligula metus, lacinia massa fusce sit massa ultricies curabitur mattis porta class sapien morbi lacinia adipiscing posuere rutrum facilisis bibendum, nullam quam sapien mi porttitor mollis cubilia a potenti, elit curabitur sapien in curabitur laoreet dui donec pulvinar praesent torquent taciti curabitur sagittis curabitur, eget primis iaculis enim sem lacinia, nibh semper pellentesque tempor habitasse.",
                        "<h1>Aliquam at rhoncus ultricies aenean fringilla varius libero aenean, lectus cubilia aliquet aenean convallis urna dui tellus, etiam aliquam luctus arcu elit ullamcorper nulla</h1>",
                        a2,
                        "School",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("FloydADixon@rhyta.com",
                        new String[]{"TracyLYancy@armyspy.com", "MichaelSPrice@jourrapide.com"},
                        new String[]{"AugustineSBoaz@jourrapide.com", "AnnaCSainz@teleworm.us"},
                        new String[]{"DaleGReynolds@armyspy.com", "VonnieLGrier@armyspy.com", "RayPGregory@dayrep.com", "AugustineSBoaz@jourrapide.com", "AnnaCSainz@teleworm.us"},
                        "metus eros vivamus accumsan", "Gravida aenean consequat dictumst urna massa ante maecenas felis, ullamcorper nibh bibendum hac lectus sit vivamus fringilla, netus est malesuada sociosqu cubilia inceptos nec congue ullamcorper commodo sem class adipiscing sagittis quis lacinia gravida eget lectus mattis non etiam, pellentesque nibh leo vulputate inceptos primis quam vulputate at rhoncus molestie platea fringilla, augue vehicula ligula dictumst litora est aliquam enim potenti euismod sed dictumst aptent et hendrerit risus ac per augue porttitor ultrices nulla eros aenean.",
                        "<h1>Ante lacinia enim nibh ipsum donec turpis leo torquent, ultricies torquent conubia malesuada ornare viverra duis taciti dui, vitae libero faucibus nam aenean dictum etiam dolor elit aliquam proin quam aliquam vel habitant justo fusce praesent fames imperdiet, nulla fames erat aliquam elementum ut porta aptent primis taciti lobortis, hendrerit fusce quam elit malesuada conubia elit adipiscing habitasse ac nullam viverra elementum pretium tellus nisi velit ligula</h1>",
                        a3,
                        "Work",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("RandyLGiles@armyspy.com",
                        new String[]{"MarkJSmith@jourrapide.com", "RodneyLNelson@jourrapide.com", "JennaLRivera@rhyta.com"},
                        new String[]{"RodneyLNelson@jourrapide.com", "JennaLRivera@rhyta.com", "TravisOLewis@armyspy.com"},
                        new String[]{"BeatriceJVogt@dayrep.com", "TracyLYancy@armyspy.com", "MichaelSPrice@jourrapide.com", "AntionetteJCarranza@jourrapide.com"},
                        "tempor magna dapibus", "Aenean tempus magna ante inceptos fringilla fermentum congue, id per habitasse vivamus lectus varius, consectetur suspendisse vulputate neque malesuada euismod donec pellentesque quisque nisi amet litora tristique donec curae, eget vel id erat libero senectus.",
                        "<p>Ipsum volutpat phasellus vel cursus aliquam volutpat augue urna aenean, libero potenti id donec eu pulvinar curae viverra quisque, aliquet lectus bibendum taciti at posuere tristique molestie blandit fringilla habitant ornare aliquam maecenas etiam lorem, elit tempus nam vivamus fusce malesuada, placerat malesuada morbi platea lorem lacus curabitur facilisis etiam vulputate augue non ante ultricies senectus ullamcorper, congue per vivamus turpis aenean libero netus dictumst vel libero, lorem vivamus tellus posuere tellus ligula nibh dapibus.</p>",
                        a4,
                        "Social",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("DebraWMartinez@armyspy.com",
                        new String[]{"FredMParmenter@rhyta.com", "JacquelineAButler@jourrapide.com"},
                        new String[]{"AugustineSBoaz@jourrapide.com", "AnnaCSainz@teleworm.us"},
                        new String[]{"DaleGReynolds@armyspy.com", "VonnieLGrier@armyspy.com"},
                        "rhoncus donec consectetur sodales", "Nullam nostra pharetra vestibulum potenti tempus augue tellus tristique hendrerit, orci enim scelerisque nisl purus nunc purus dolor inceptos fermentum quam ornare vel aptent facilisis massa pellentesque quam fames orci tellus cubilia vulputate, suspendisse hendrerit ante aliquam phasellus etiam taciti viverra class cubilia nisl convallis aptent accumsan sit nostra commodo pretium erat venenatis blandit morbi ut egestas magna mi per, lobortis venenatis congue dolor arcu amet pharetra sagittis tristique curabitur scelerisque porttitor nunc dictumst luctus eleifend.",
                        "<p>Phasellus dapibus curabitur varius eleifend nisl enim quis arcu dui fames, habitasse nisl ut sollicitudin elementum tempor amet rutrum mollis, ultricies eleifend porttitor sagittis luctus lorem rutrum euismod curabitur sapien himenaeos curae morbi nunc scelerisque porta nisl ante dictumst viverra nostra fusce, sed habitant a cursus tristique nam aliquam magna urna integer euismod ullamcorper sociosqu vitae sed class porttitor convallis class condimentum porttitor dapibus etiam lectus, venenatis lacus tellus sodales dictum vehicula sollicitudin lacinia platea lacinia elementum duis ut amet per.</p>",
                        a5,
                        "Inbox",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("WillieBGoris@jourrapide.com",
                        new String[]{"VonnieLGrier@armyspy.com", "RayPGregory@dayrep.com", "AugustineSBoaz@jourrapide.com"},
                        new String[]{"RobinKSchulze@dayrep.com", "BarbaraARogers@armyspy.com", "FredMParmenter@rhyta.com", "JacquelineAButler@jourrapide.com", "TimothyCGuy@dayrep.com"},
                        new String[]{"TonyCMorales@armyspy.com", "BeatriceJVogt@dayrep.com", "TracyLYancy@armyspy.com", "MichaelSPrice@jourrapide.com", "AntionetteJCarranza@jourrapide.com"},
                        "ante interdum lobortis vulputate", "Etiam bibendum aenean massa neque sed ad vitae ut, congue vivamus senectus egestas proin nisi ante litora, nisi auctor amet fermentum fringilla aliquet mattis sem aptent vestibulum eros lobortis turpis auctor pharetra et, pretium cursus metus inceptos auctor aenean platea nisl ligula varius ut ullamcorper fermentum vitae egestas nam, dictum ante malesuada habitasse arcu turpis semper nunc nulla, a id felis duis per ut ultrices suscipit.",
                        "<p>Id posuere id sit ultrices nisi porttitor, lacinia accumsan quam eros fusce, ullamcorper litora consectetur vitae sed venenatis placerat accumsan donec quam quis ut semper aenean ligula metus, lacinia massa fusce sit massa ultricies curabitur mattis porta class sapien morbi lacinia adipiscing posuere rutrum facilisis bibendum, nullam quam sapien mi porttitor mollis cubilia a potenti, elit curabitur sapien in curabitur laoreet dui donec pulvinar praesent torquent taciti curabitur sagittis curabitur, eget primis iaculis enim sem lacinia, nibh semper pellentesque tempor habitasse.</p>",
                        a4,
                        "Inbox",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("GaryEDimattia@armyspy.com",
                        new String[]{"TracyLYancy@armyspy.com", "MichaelSPrice@jourrapide.com"},
                        new String[]{"MarkJSmith@jourrapide.com", "RodneyLNelson@jourrapide.com", "JennaLRivera@rhyta.com"},
                        new String[]{"MarkJSmith@jourrapide.com", "RodneyLNelson@jourrapide.com", "JennaLRivera@rhyta.com", "TravisOLewis@armyspy.com"},
                        "fermentum curabitur etiam orci ", "Nullam nostra pharetra vestibulum potenti tempus augue tellus tristique hendrerit, orci enim scelerisque nisl purus nunc purus dolor inceptos fermentum quam ornare vel aptent facilisis massa pellentesque quam fames orci tellus cubilia vulputate, suspendisse hendrerit ante aliquam phasellus etiam taciti viverra class cubilia nisl convallis aptent accumsan sit nostra commodo pretium erat venenatis blandit morbi ut egestas magna mi per, lobortis venenatis congue dolor arcu amet pharetra sagittis tristique curabitur scelerisque porttitor nunc dictumst luctus eleifend.",
                        "<p>Gravida aenean consequat dictumst urna massa ante maecenas felis, ullamcorper nibh bibendum hac lectus sit vivamus fringilla, netus est malesuada sociosqu cubilia inceptos nec congue ullamcorper commodo sem class adipiscing sagittis quis lacinia gravida eget lectus mattis non etiam, pellentesque nibh leo vulputate inceptos primis quam vulputate at rhoncus molestie platea fringilla, augue vehicula ligula dictumst litora est aliquam enim potenti euismod sed dictumst aptent et hendrerit risus ac per augue porttitor ultrices nulla eros aenean.</p>",
                        a6,
                        "School",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("DaleGReynolds@armyspy.com",
                        new String[]{"FloydADixon@rhyta.com", "RandyLGiles@armyspy.com", "DebraWMartinez@armyspy.com", "WillieBGoris@jourrapide.com", "GaryEDimattia@armyspy.com"},
                        new String[]{"BeatriceJVogt@dayrep.com", "TracyLYancy@armyspy.com", "MichaelSPrice@jourrapide.com", "AntionetteJCarranza@jourrapide.com"},
                        new String[]{"JennaLRivera@rhyta.com", "TravisOLewis@armyspy.com", "RayDStewart@dayrep.com"},
                        "ligula imperdiet, lectus ",
                        "Habitasse cras magna interdum convallis ultrices faucibus libero vestibulum orci maecenas porta, netus sit tincidunt egestas auctor sociosqu blandit primis auctor lacinia pharetra, volutpat nisi adipiscing ut etiam adipiscing nunc in metus sollicitudin primis ante tortor vel potenti nibh risus maecenas, himenaeos ultrices eros lorem vulputate eros condimentum per, vehicula gravida sociosqu cras egestas porttitor.",
                        "<p>Aenean tempus magna ante inceptos fringilla fermentum congue, id per habitasse vivamus lectus varius, consectetur suspendisse vulputate neque malesuada euismod donec pellentesque quisque nisi amet litora tristique donec curae, eget vel id erat libero senectus.</p>",
                        a3,
                        "Inbox",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("VonnieLGrier@armyspy.com",
                        new String[]{"FredMParmenter@rhyta.com", "JacquelineAButler@jourrapide.com"},
                        new String[]{"MarkJSmith@jourrapide.com", "RodneyLNelson@jourrapide.com", "JennaLRivera@rhyta.com"},
                        new String[]{"MarkJSmith@jourrapide.com", "RodneyLNelson@jourrapide.com", "JennaLRivera@rhyta.com", "TravisOLewis@armyspy.com", "RayDStewart@dayrep.com"},
                        "proin nam urna iaculis adipiscing", "Dui faucibus tempor vehicula morbi adipiscing sed arcu ullamcorper, aenean orci eros lacinia habitant aptent quisque, odio justo convallis donec libero non elit.",
                        "<p>Nullam nostra pharetra vestibulum potenti tempus augue tellus tristique hendrerit, orci enim scelerisque nisl purus nunc purus dolor inceptos fermentum quam ornare vel aptent facilisis massa pellentesque quam fames orci tellus cubilia vulputate, suspendisse hendrerit ante aliquam phasellus etiam taciti viverra class cubilia nisl convallis aptent accumsan sit nostra commodo pretium erat venenatis blandit morbi ut egestas magna mi per, lobortis venenatis congue dolor arcu amet pharetra sagittis tristique curabitur scelerisque porttitor nunc dictumst luctus eleifend.</p>",
                        a7,
                        "Social",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("RayPGregory@dayrep.com",
                        new String[]{"TracyLYancy@armyspy.com", "MichaelSPrice@jourrapide.com"},
                        new String[]{"DaleGReynolds@armyspy.com", "VonnieLGrier@armyspy.com", "RayPGregory@dayrep.com", "AugustineSBoaz@jourrapide.com", "AnnaCSainz@teleworm.us"},
                        new String[]{"MarkJSmith@jourrapide.com", "RodneyLNelson@jourrapide.com", "JennaLRivera@rhyta.com", "TravisOLewis@armyspy.com"},
                        "at dictumst sollicitudin nec", "Condimentum ad arcu mollis tortor commodo ut odio dolor consequat, curabitur euismod malesuada consequat quisque placerat duis lectus sed, viverra libero lacus turpis donec mi convallis lacus arcu platea mi tellus tempor class cubilia aliquam habitant pulvinar, porta netus nisi sem mi curabitur iaculis consectetur luctus, litora aliquam inceptos orci taciti vivamus platea enim justo feugiat risus nec ac donec, arcu magna ut libero neque, proin primis felis etiam semper sed nec ultrices velit donec eleifend fringilla felis facilisis elit.",
                        "<p>Habitasse cras magna interdum convallis ultrices faucibus libero vestibulum orci maecenas porta, netus sit tincidunt egestas auctor sociosqu blandit primis auctor lacinia pharetra, volutpat nisi adipiscing ut etiam adipiscing nunc in metus sollicitudin primis ante tortor vel potenti nibh risus maecenas, himenaeos ultrices eros lorem vulputate eros condimentum per, vehicula gravida sociosqu cras egestas porttitor.</p>",
                        a1,
                        "Social",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("AugustineSBoaz@jourrapide.com",
                        new String[]{"RobinKSchulze@dayrep.com", "BarbaraARogers@armyspy.com", "FredMParmenter@rhyta.com", "JacquelineAButler@jourrapide.com", "TimothyCGuy@dayrep.com"},
                        new String[]{"BeatriceJVogt@dayrep.com", "TracyLYancy@armyspy.com", "MichaelSPrice@jourrapide.com", "AntionetteJCarranza@jourrapide.com"},
                        new String[]{"JennaLRivera@rhyta.com", "TravisOLewis@armyspy.com", "RayDStewart@dayrep.com"},
                        "Proin molestie tortor volutpat sodales metus", "Ornare posuere ullamcorper posuere mauris placerat vivamus duis curae, tristique quisque suscipit condimentum euismod posuere enim, sociosqu proin pulvinar auctor dapibus metus convallis diam nunc libero proin nunc etiam pellentesque aliquam arcu metus eu magna, cras fermentum condimentum aliquam porta sapien eros torquent augue tempus, sociosqu class felis curae consectetur est nam aliquet nam est sodales elementum nisl feugiat orci hendrerit ac vehicula turpis facilisis congue himenaeos nibh class risus, congue etiam purus rutrum ligula venenatis aliquet turpis commodo proin magna vestibulum.",
                        "<p>Dui faucibus tempor vehicula morbi adipiscing sed arcu ullamcorper, aenean orci eros lacinia habitant aptent quisque, odio justo convallis donec libero non elit.</p>",
                        a2,
                        "Sent",
                        new Timestamp(System.currentTimeMillis())));

        this.dao.pushEmail(
                new EmailBean("AnnaCSainz@teleworm.us",
                        new String[]{"DaleGReynolds@armyspy.com", "VonnieLGrier@armyspy.com", "RayPGregory@dayrep.com", "AugustineSBoaz@jourrapide.com", "AnnaCSainz@teleworm.us"},
                        new String[]{"FloydADixon@rhyta.com", "RandyLGiles@armyspy.com", "DebraWMartinez@armyspy.com", "WillieBGoris@jourrapide.com", "GaryEDimattia@armyspy.com"},
                        new String[]{"MarkJSmith@jourrapide.com", "RodneyLNelson@jourrapide.com", "JennaLRivera@rhyta.com", "TravisOLewis@armyspy.com", "RayDStewart@dayrep.com"},
                        "viverra donec duis, felis feugiat", "Commodo orci rutrum nam suscipit vehicula in lorem inceptos conubia, vestibulum sollicitudin commodo ante senectus et lorem.",
                        "<p>Condimentum ad arcu mollis tortor commodo ut odio dolor consequat, curabitur euismod malesuada consequat quisque placerat duis lectus sed, viverra libero lacus turpis donec mi convallis lacus arcu platea mi tellus tempor class cubilia aliquam habitant pulvinar, porta netus nisi sem mi curabitur iaculis consectetur luctus, litora aliquam inceptos orci taciti vivamus platea enim justo feugiat risus nec ac donec, arcu magna ut libero neque, proin primis felis etiam semper sed nec ultrices velit donec eleifend fringilla felis facilisis elit.</p>",
                        a3,
                        "Inbox",
                        new Timestamp(System.currentTimeMillis())));

    }

}
