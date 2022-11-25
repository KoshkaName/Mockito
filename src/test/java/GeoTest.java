import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;


public class GeoTest {

    @Test
    public void TestMessageRu() {
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(GeoServiceImpl.MOSCOW_IP)).thenReturn(new Location(null, Country.RUSSIA, null, 0));
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать ");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> text = new HashMap<>();
        text.put(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.MOSCOW_IP);
        String expect = "Добро пожаловать ";
        String result = messageSender.send(text);
        Assertions.assertEquals(expect, result);
        System.out.println();
    }


    @Test
    public void TestMessageUA() {
        GeoService geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(GeoServiceImpl.NEW_YORK_IP)).thenReturn(new Location(null, Country.USA, null, 0));
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome ");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> text = new HashMap<>();
        text.put(MessageSenderImpl.IP_ADDRESS_HEADER, GeoServiceImpl.NEW_YORK_IP);
        String expect = "Welcome ";
        String result = messageSender.send(text);
        Assertions.assertEquals(expect, result);
    }

    @Test
    public void TestGeoLocationRu() {
        GeoService geoService = new GeoServiceImpl();
        Country expect = Country.RUSSIA;
        Country result = geoService.byIp(GeoServiceImpl.MOSCOW_IP).getCountry();
        Assertions.assertEquals(expect, result);
    }
    @Test
    public void TestGeoLocationUA() {
        GeoService geoService = new GeoServiceImpl();
        Country expect = Country.USA;
        Country result = geoService.byIp(GeoServiceImpl.NEW_YORK_IP).getCountry();
        Assertions.assertEquals(expect, result);
    }

    @Test
    public void TestLocalizationRu() {
        LocalizationService localizationService = new LocalizationServiceImpl();
        String expect = "Добро пожаловать";
        String result = localizationService.locale(Country.RUSSIA);
        Assertions.assertEquals(expect, result);
    }
    @Test
    public void TestLocalizationUA() {
        LocalizationService localizationService = new LocalizationServiceImpl();
        String expect = "Welcome";
        String result = localizationService.locale(Country.USA);
        Assertions.assertEquals(expect, result);
    }
}
