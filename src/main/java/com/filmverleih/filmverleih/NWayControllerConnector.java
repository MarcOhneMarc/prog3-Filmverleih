package com.filmverleih.filmverleih;

import java.util.Optional;

/**
 * A class to connect multiple controllers, for easier access to attributes accross different scenes
 * @param <Q> the first Controller, here: NavbarController
 * @param <R> the second controller, here: LibraryController
 * @param <S> the third controller, here: MovieController
 * @param <T> the fourth controller, here: RentalController
 * @param <U> the fifth controller, here: SettingsController
 * @param <V> the sixth controller, here: FilterController
 * @param <W> the seventh controller, here: CartController
 * @param <X> the eighth controller, here: EditMovieController
 * @param <Y> currently unused, for future expansion
 * @param <Z> currently unused, for future expansion
 */
public class NWayControllerConnector<Q,R,S,T,U,V,W,X,Y,Z> {

    private Q navbarController;
        private R libraryController;
        private S movieController;
        private T rentalController;
        private U settingsController;
        private V filterController;
        private W cartController;
        private X editMovieController;
        private Optional <Y> controller9;
        private Optional <Z> controller10;
        public NWayControllerConnector(Q controller1, R controller2, S controller3, T controller4, U controller5, V controller6, W controller7, X controller8) {
            this.navbarController = controller1;
            this.libraryController = controller2;
            this.movieController = controller3;
            this.rentalController = controller4;
            this.settingsController = controller5;
            this.filterController = controller6;
            this.cartController = controller7;
            this.editMovieController = controller8;
            this.controller9 = Optional.empty();
            this.controller10 = Optional.empty();
        }
        public NWayControllerConnector(Q controller1, R controller2, S controller3, T controller4, U controller5, V controller6, W controller7, X controller8, Y controller9, Z controller10) {
            this.navbarController = controller1;
            this.libraryController = controller2;
            this.movieController = controller3;
            this.rentalController = controller4;
            this.settingsController = controller5;
            this.filterController = controller6;
            this.cartController = controller7;
            this.editMovieController = controller8;
            this.controller9 = Optional.ofNullable(controller9);
            this.controller10 = Optional.ofNullable(controller10);
    }

    /**
     * @return returns first Controller, here NavbarController
     */
    public Q getNavbarController() {
        return navbarController;
    }

    /**
     * @return returns second controller, here LibraryController
     */
    public R getLibraryController() {
        return libraryController;
    }

    /**
     * @return returns the third controller, here MovieController
     */
    public S getMovieController() {
        return movieController;
    }

    /**
     * @return returns the fourth controller, here RentalController
     */
    public T getRentalController() {
        return rentalController;
    }

    /**
     * @return returns the fifth controller, here SettingsController
     */
    public U getSettingsController() {
        return settingsController;
    }

    /**
     * @return returns sixth controller, here : FilterController
     */
    public V getFilterController() { return filterController; }

    /**
     * @return returns seventh controller, here : CartController
     */
    public W getCartController() {
        return cartController;
    }

    /**
     * @return returns eighth controller (if present), EditMovieController
     */
    public X getEditMovieController() {
        return editMovieController;
    }

    /**
     * @return returns ninth controller (if present) currently unused
     */
    public Optional<Y> getController9() { return controller9; }

    /**
     * @return returns tenth controller (if present) currently unused
     */
    public Optional<Z> getController10() {
        return controller10;
    }
}