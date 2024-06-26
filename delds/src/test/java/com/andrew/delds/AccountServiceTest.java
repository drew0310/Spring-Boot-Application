package com.andrew.delds;

import com.andrew.delds.exception.ObjectNotFoundException;
import com.andrew.delds.model.Account;
import com.andrew.delds.repo.AccountRepo;
import com.andrew.delds.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepo accountRepo;

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account();
        account.setId(7);
        account.setGuid("sample GUID");
        account.setOwnerid(7);
        account.setLocked(0);
        account.setMaxconlimit(100);

    }




    @Test
    public void testCreateAccount() {
        when(accountRepo.save(any(Account.class))).thenReturn(account);

        Account createdAccount = accountService.saveAccount(account);

        assertNotNull(createdAccount, "The saved account should not be null");
        assertEquals(account, createdAccount, "The saved account should match the original input account");
        verify(accountRepo, times(1)).save(account);
    }

    @Test
    public void testCreateAccounts() {
        when(accountRepo.saveAll(anyList())).thenReturn(Collections.singletonList(account));

        List<Account> createdAccounts = accountService.saveAccounts(Collections.singletonList(account));

        assertNotNull(createdAccounts, "The saved accounts list should not be null");
        assertEquals(1, createdAccounts.size(), "The size of the saved accounts list should be 1");
        assertEquals(account, createdAccounts.getFirst(), "The account in the saved accounts list should match the original input account");

        verify(accountRepo, times(1)).saveAll(Collections.singletonList(account));
    }

    @Test
    public void testGetAccountsNotEmpty() throws ObjectNotFoundException {

        when(accountRepo.findAll()).thenReturn(Collections.singletonList(account));

        List<Account> fetchedAccounts = accountService.getAccounts();

        assertNotNull(fetchedAccounts, "The fetched accounts list should not be null");
        assertEquals(1, fetchedAccounts.size(), "The size of the fetched accounts list should be 1");
        assertEquals(account, fetchedAccounts.getFirst(), "The account in the fetched accounts list should match the original input account");
        verify(accountRepo, times(1)).findAll();

    }

    @Test
    public void testGetAccountsEmpty() {
        when(accountRepo.findAll()).thenReturn(Collections.emptyList());

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            accountService.getAccounts();
        });


    }

    @Test
    public void testGetAccountByIdNotEmpty() throws ObjectNotFoundException {
        when(accountRepo.findById(7)).thenReturn(Optional.of(account));

        Account fetchedAccount = accountService.getAccountById(7);
        assertNotNull(fetchedAccount, "The fetched account should not be null");
        assertEquals(account, fetchedAccount, "The fetched account must match the original input account");
        verify(accountRepo, times(1)).findById(7);

    }

    @Test
    public void testGetAccountByIdEmpty() {
        when(accountRepo.findById(18)).thenReturn(Optional.empty());

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            accountService.getAccountById(18);
        });
    }

    @Test
    public void testGetAccountByGuidNotEmpty() throws ObjectNotFoundException {
        when(accountRepo.findByGuid("sample GUID")).thenReturn(account);

        Account fetchedAccount = accountService.getAccountByGuid("sample GUID");
        assertNotNull(fetchedAccount, "The fetched account should not be null");
        assertEquals(account, fetchedAccount, "The fetched account must match the original input account");
        verify(accountRepo, times(1)).findByGuid("sample GUID");

    }

    @Test
    public void testGetAccountByGuidEmpty() {
        when(accountRepo.findByGuid("sample GUID")).thenReturn(null);

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            accountService.getAccountByGuid("sample GUID");
        });
    }

    @Test
    public void testGetAccountByOwneridNotEmpty() throws ObjectNotFoundException {
        when(accountRepo.findByOwnerid(7)).thenReturn(account);

        Account fetchedAccount = accountService.getAccountByOwnerid(7);
        assertNotNull(fetchedAccount, "The fetched account should not be null");
        assertEquals(account, fetchedAccount, "The fetched account must match the original input account");
        verify(accountRepo, times(1)).findByOwnerid(7);

    }

    @Test
    public void testGetAccountByOwneridEmpty() {
        when(accountRepo.findByOwnerid(7)).thenReturn(null);

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            accountService.getAccountByOwnerid(7);
        });
    }

    @Test
    public void testDeleteAccountNotEmpty() throws ObjectNotFoundException {
        when(accountRepo.findById(7)).thenReturn(Optional.of(account));
        doNothing().when(accountRepo).deleteById(7);

        accountService.deleteAccount(7);
        verify(accountRepo, times(1)).findById(7);
        verify(accountRepo, times(1)).deleteById(7);
    }

    @Test
    public void testDeleteAccountEmpty() {
        when(accountRepo.findById(7)).thenReturn(Optional.empty());

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            accountService.getAccountById(7);
        });
    }

    @Test
    public void testUpdateAccountNotEmpty() throws ObjectNotFoundException {
        Account updatedAccount = new Account();
        updatedAccount.setId(7);
        updatedAccount.setGuid("sample GUID 2");
        updatedAccount.setOwnerid(7);
        updatedAccount.setMaxconlimit(40);
        updatedAccount.setLocked(1);

        when(accountRepo.findById(7)).thenReturn(Optional.of(account));
        when(accountRepo.save(any(Account.class))).thenReturn(updatedAccount);

        Account newAccount = accountService.updateAccount(updatedAccount);

        assertNotNull(newAccount, "The updated account should not be null");
        assertEquals(updatedAccount, newAccount, "The updated account should match the original input account");
        verify(accountRepo, times(1)).findById(7);

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepo, times(1)).save(accountCaptor.capture());

        Account savedAccount = accountCaptor.getValue();
        assertEquals(updatedAccount.getId(), savedAccount.getId(), "The ID of the saved account should match the updated account's ID");
        assertEquals(updatedAccount.getGuid(), savedAccount.getGuid(), "The GUID of the saved account should match the updated account's GUID");
        assertEquals(updatedAccount.getOwnerid(), savedAccount.getOwnerid(), "The Owner ID of the saved account should match the updated account's Owner ID");
        assertEquals(updatedAccount.getMaxconlimit(), savedAccount.getMaxconlimit(), "The Maxconlimit of the saved account should match the updated account's Maxconlimit");
        assertEquals(updatedAccount.getLocked(), savedAccount.getLocked(), "The Locked status of the saved account should match the updated account's Locked status");

    }

    @Test
    public void testUpdateAccountEmpty() {
        Account updatedAccount = new Account();
        updatedAccount.setId(7);
        updatedAccount.setGuid("sample GUID 2");
        updatedAccount.setOwnerid(7);
        updatedAccount.setMaxconlimit(40);
        updatedAccount.setLocked(1);

        when(accountRepo.findById(7)).thenReturn(Optional.empty());


        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> {
            accountService.updateAccount(updatedAccount);
        });

        verify(accountRepo, times(1)).findById(7);
        verify(accountRepo, times(0)).save(any(Account.class));
    }










}
