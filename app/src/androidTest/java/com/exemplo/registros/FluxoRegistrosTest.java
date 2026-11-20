package com.exemplo.registros;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class FluxoRegistrosTest {
    @Test
    public void cadastrarConsultarEditarEExcluir() {
        int totalInicial = RegistroRepositorio.total();
        try (ActivityScenario<MainActivity> tela = ActivityScenario.launch(MainActivity.class)) {
            onView(withId(R.id.btnNovoRegistro)).perform(scrollTo(), click());
            onView(withId(R.id.etTitulo)).perform(replaceText("Inspeção de teste"), closeSoftKeyboard());
            onView(withId(R.id.btnSalvar)).perform(click());
            onView(withId(R.id.tvTotalRegistros)).check(matches(withText(String.valueOf(totalInicial + 1))));

            onView(withId(R.id.btnVerRegistros)).perform(scrollTo(), click());
            onData(anything()).inAdapterView(withId(R.id.listRegistros)).atPosition(0).perform(click());
            onView(withId(R.id.tvDetalheTitulo)).check(matches(withText("Inspeção de teste")));
            onView(withId(R.id.btnEditar)).perform(click());
            onView(withId(R.id.etTitulo)).check(matches(withText("Inspeção de teste")))
                    .perform(replaceText("Inspeção concluída"), closeSoftKeyboard());
            onView(withId(R.id.btnSalvar)).perform(click());
            onView(withId(R.id.tvDetalheTitulo)).check(matches(withText("Inspeção concluída")));

            onView(withId(R.id.btnExcluir)).perform(click());
            onView(withText(R.string.dialogo_nao)).inRoot(isDialog()).perform(click());
            onView(withId(R.id.tvDetalheTitulo)).check(matches(withText("Inspeção concluída")));
            onView(withId(R.id.btnExcluir)).perform(click());
            onView(withText(R.string.dialogo_sim)).inRoot(isDialog()).perform(click());
            onView(withId(R.id.tvQuantidade)).check(matches(withText(String.valueOf(totalInicial))));
            onView(withId(R.id.btnVoltarLista)).perform(click());
            onView(withId(R.id.tvTotalRegistros)).check(matches(withText(String.valueOf(totalInicial))));
        }
    }
}
