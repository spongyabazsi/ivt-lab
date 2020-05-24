package hu.bme.mit.spaceship;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore torpedostore1;
  private TorpedoStore torpedostore2;

  @BeforeEach
  public void init(){
    torpedostore1 = mock(TorpedoStore.class);
    torpedostore2 = mock(TorpedoStore.class);

    this.ship = new GT4500(torpedostore1,torpedostore2);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    when(torpedostore1.fire(1)).thenReturn(true);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(true, result);
    verify(torpedostore1, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
           when(this.torpedostore1.fire(1)).thenReturn(true);
           when(this.torpedostore2.fire(1)).thenReturn(true);

           boolean result = ship.fireTorpedo(FiringMode.ALL);
           assertEquals(true, result);

           verify(this.torpedostore1, times(1)).fire(1);
           verify(this.torpedostore2, times(1)).fire(1);
  }
  @Test
  public void fireTorpedo_Single_FirePrimaryFirst() {
    when(this.torpedostore1.fire(any(int.class))).thenReturn(true);
    when(this.torpedostore2.fire(any(int.class))).thenReturn(true);
    this.ship.fireTorpedo(FiringMode.SINGLE);
    verify(this.torpedostore1, times(1)).fire(any(int.class));
    verify(this.torpedostore2, times(0)).fire(any(int.class));
  }
  @Test
  public void fireTorpedo_Single_FireOnebyOne() {
  
    when(this.torpedostore1.fire(any(int.class))).thenReturn(true);
    when(this.torpedostore2.fire(any(int.class))).thenReturn(true);

    this.ship.fireTorpedo(FiringMode.SINGLE);
    this.ship.fireTorpedo(FiringMode.SINGLE);
    this.ship.fireTorpedo(FiringMode.SINGLE);
    this.ship.fireTorpedo(FiringMode.SINGLE);
    this.ship.fireTorpedo(FiringMode.SINGLE);
    this.ship.fireTorpedo(FiringMode.SINGLE);
    this.ship.fireTorpedo(FiringMode.SINGLE);

    verify(this.torpedostore1, times(4)).fire(any(int.class));
    verify(this.torpedostore2, times(3)).fire(any(int.class));
  }
  @Test
  public void fireTorpedo_Single_FireSndIfFirstEmpty() {
    when(this.torpedostore1.fire(any(int.class))).thenReturn(true);
    when(this.torpedostore2.fire(any(int.class))).thenReturn(true);
    when(this.torpedostore1.isEmpty()).thenReturn(true);
    this.ship.fireTorpedo(FiringMode.SINGLE);
    verify(this.torpedostore1, times(0)).fire(any(int.class));
    verify(this.torpedostore2, times(1)).fire(any(int.class));
  }
  
  @Test
  public void fireTorpedo_Single_FireFirstandSecondEmpty() {
    when(this.torpedostore1.fire(any(int.class))).thenReturn(true);
    when(this.torpedostore2.fire(any(int.class))).thenReturn(true);
    when(this.torpedostore2.isEmpty()).thenReturn(true);
    when(this.torpedostore1.isEmpty()).thenReturn(false);
    this.ship.fireTorpedo(FiringMode.SINGLE);
    this.ship.fireTorpedo(FiringMode.SINGLE);
    verify(this.torpedostore1, times(2)).fire(any(int.class));
    verify(this.torpedostore2, times(0)).fire(any(int.class));
  }  
  @Test
  public void fireTorpedo_Single_BothEmptySecondaryIsNext() {

    when(this.torpedostore1.fire(any(int.class))).thenReturn(true);
    when(this.torpedostore2.fire(any(int.class))).thenReturn(true);
    when(this.torpedostore2.isEmpty()).thenReturn(true);
    when(this.torpedostore1.isEmpty()).thenReturn(false);

    this.ship.fireTorpedo(FiringMode.SINGLE);
    when(this.torpedostore1.isEmpty()).thenReturn(true);
    this.ship.fireTorpedo(FiringMode.SINGLE);

    verify(this.torpedostore1, times(1)).fire(any(int.class));
    verify(this.torpedostore2, times(0)).fire(any(int.class));
  }
  @Test
  public void fireTorpedo_Single_BothEmpty() {
    when(this.torpedostore1.fire(any(int.class))).thenReturn(true);
    when(this.torpedostore2.fire(any(int.class))).thenReturn(true);
    when(this.torpedostore2.isEmpty()).thenReturn(true);
    when(this.torpedostore1.isEmpty()).thenReturn(true);
    this.ship.fireTorpedo(FiringMode.SINGLE);
    this.ship.fireTorpedo(FiringMode.SINGLE);
    this.ship.fireTorpedo(FiringMode.SINGLE);
    this.ship.fireTorpedo(FiringMode.SINGLE);
    verify(this.torpedostore1, times(0)).fire(any(int.class));
    verify(this.torpedostore2, times(0)).fire(any(int.class));
  }

  @Test
  public void fireTorpedo_Null() {
    try {
      this.ship.fireTorpedo(null);
    } catch (NullPointerException exception) {
      assertEquals(true, true);
    }
  }

  @Test
  public void fireLaser_NotImplemented() {
    boolean result = this.ship.fireLaser(FiringMode.ALL);    

    assertEquals(false, result);
  }


  @Test
  public void fireTorpedo_All_FireFirstIfSecondEmpty() {
    when(this.torpedostore1.fire(any(int.class))).thenReturn(true);
    when(this.torpedostore2.fire(any(int.class))).thenReturn(true);
    when(this.torpedostore2.isEmpty()).thenReturn(true);
    this.ship.fireTorpedo(FiringMode.ALL);
    this.ship.fireTorpedo(FiringMode.ALL);
    this.ship.fireTorpedo(FiringMode.ALL);
    this.ship.fireTorpedo(FiringMode.ALL);
    verify(this.torpedostore1, times(4)).fire(any(int.class));
    verify(this.torpedostore2, times(0)).fire(any(int.class));
  }
  @Test
  public void fireTorpedo_All_FireOtherOnceIfEmpty() {

    when(this.torpedostore1.fire(any(int.class))).thenReturn(true);
    when(this.torpedostore2.fire(any(int.class))).thenReturn(true);
    when(this.torpedostore1.isEmpty()).thenReturn(true);

    boolean result = this.ship.fireTorpedo(FiringMode.ALL);

    verify(this.torpedostore1, times(0)).fire(any(int.class));
    verify(this.torpedostore2, times(1)).fire(any(int.class));
    assertEquals(true, result);
  }


}
