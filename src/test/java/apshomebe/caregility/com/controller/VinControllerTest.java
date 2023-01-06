package apshomebe.caregility.com.controller;

import apshomebe.caregility.com.controllers.VinController;
import apshomebe.caregility.com.exception.MachineNotFoundException;
import apshomebe.caregility.com.models.Vin;
import apshomebe.caregility.com.payload.VinResponce;
import apshomebe.caregility.com.service.VinService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VinControllerTest {

    @InjectMocks
    VinController vinController;
    @Mock
    VinService vinService;

    @Test
    public void testCreateVin() {
        when(vinService.createNewVin()).thenReturn(getVin());
        ResponseEntity<?> result = vinController.createVin();
        assertThat(result.getStatusCode().value()).isEqualTo(200);
        assertThat(result.getStatusCode().toString()).isEqualTo("200 OK");
    }
@Test
    public void testGetVin() {
        try {
            when(vinService.getVinAgainstMachineName(Mockito.anyString())).thenReturn(getVinData().getBody());
            ResponseEntity<?> result =vinController.getVin("DESK9599GUV");
            assertThat(result.getStatusCode().value()).isEqualTo(200);
            assertThat(result.getBody()).isInstanceOf(VinResponce.class);
        } catch (MachineNotFoundException e) {

        }
    }
    @Test
    public void testMapVin()
    {
        try {
            when(vinService.mapVindetails(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
            ResponseEntity<?> result =vinController.mapVin("acd1123456fgd01112022025254","DESK9599GUV");
            assertThat(result.getStatusCode().value()).isEqualTo(200);


        }catch (Exception e)
        {

        }
    }
    @Test
    public void testDeleteVin()
    {
        try{
        Mockito.doNothing().when(vinService).deleteVin(Mockito.anyString());
        vinController.deleteVin("acd1123456fgd01112022021954");
        }catch (Exception e)
        {

        }

    }
    @Test
    public void TestGetVinDetails()
    {
        when(vinService.getVinDetails(Mockito.anyString())).thenReturn(getVinListData());
        ResponseEntity<List<VinResponce>> result=    vinController.getVinDetails("acd1123456fgd01112022021954");
        assertThat(result.getBody().size()).isEqualTo(1);
    }
    @Test
    public void TestClearMapping()
    {
        try {

            doNothing().when(vinService).clearMapping(Mockito.anyString());
            vinController.clearMapping("acd1123456fgd01112022021954");

        }catch (Exception e)
        {

        }
       }
        @Test
       public void testExportVinDetails()
       {

           when(vinService.getVinExportDetails(any(Pageable.class))).thenReturn(null);
           vinController.exportVinDetails(10,20);
       }

    private List<VinResponce> getVinListData() {

        return Arrays.asList(getVinData().getBody());

    }

// Test data starts here

    private Vin getVin() {

        Vin vin = new Vin();
        vin.setVinNumber("acd1123456fgd01112022021954");
        return vin;

    }


    private ResponseEntity<VinResponce> getVinData()
    {
        VinResponce responce=new VinResponce();
        responce.setId("acd1123456fgd01112022025254");
       return ResponseEntity.ok(responce);
    }

}
