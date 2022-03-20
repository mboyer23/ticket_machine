package ticketmachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
	// S1 : le prix affiché correspond à l’initialisation
	public void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	public void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
                // Les montants ont été correctement additionnés  
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");              
	}

	@Test
	// S3 : on n’imprime pas le ticket si le montant inséré est insuffisant
	public void noTicketIfAmountNotSufficient() {
		assertFalse(machine.printTicket());
	}

	@Test
	// S4 : on imprime le ticket si le montant inséré est suffisant
	public void ticketPrintedIfAmoutSufficient() {
		machine.insertMoney(50);
		assertTrue(machine.printTicket());
	}

	@Test
	// S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
	public void priceDecrementedAfterTicketPrint() {
		machine.insertMoney(50);
		machine.printTicket();
		assertEquals(0, machine.getBalance());
	}

	@Test
	// S6 : le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	public void amountUpdatedAfterTicketPrint() {
		machine.insertMoney(50);
		machine.printTicket();
		assertEquals(50, machine.getTotal());
	}

	@Test
	// S7 : refund() rend correctement la monnaie
	public void correctRefund() {
		machine.insertMoney(50);
		assertEquals(machine.refund(), machine.getBalance());
	}

	@Test
	// S8 : refund() remet la balance à zéro
	public void balanceIsResetByRefund(){
		machine.insertMoney(50);
		machine.refund();
		assertEquals(0, machine.getBalance());
	}

	@Test
	// S9 : on ne peut pas insérer un montant négatif
	public void negativeInsertionIsProhibited() {
		assertThrows(IllegalArgumentException.class, () -> {machine.insertMoney(-50);});
	}

	@Test
	// S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	public void cannotCreateNegativeTicketMachine() {
		assertThrows(IllegalArgumentException.class, () -> {new TicketMachine(-50);});
	}
}
